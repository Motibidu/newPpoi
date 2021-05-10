package org.dstadler.poiandroidtest.newpoi;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.XmlToken;
import org.openxmlformats.schemas.drawingml.x2006.main.CTGraphicalObject;
import org.openxmlformats.schemas.drawingml.x2006.main.CTNonVisualDrawingProps;
import org.openxmlformats.schemas.drawingml.x2006.main.CTPositiveSize2D;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTAnchor;
import org.openxmlformats.schemas.drawingml.x2006.wordprocessingDrawing.CTInline;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBookmark;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTDrawing;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class CustomXWPFDocument extends XWPFDocument {
    public CustomXWPFDocument() {
        super();
    }

    public CustomXWPFDocument(OPCPackage opcPackage) throws IOException {
        super(opcPackage);
    }

    public CustomXWPFDocument(InputStream in) throws IOException {
        super(in);
    }

    public void addPictureToRun(XWPFRun run, String blipId, int id, int width, int height) {
        final int EMU = 9525;
//        width *= EMU;
//        height *= EMU;

        String picXml = "" +
                "<a:graphic xmlns:a=\"http://schemas.openxmlformats.org/drawingml/2006/main\">" +
                "   <a:graphicData uri=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">" +
                "      <pic:pic xmlns:pic=\"http://schemas.openxmlformats.org/drawingml/2006/picture\">" +
                "         <pic:nvPicPr>" +
                "            <pic:cNvPr id=\"" + id + "\" name=\"Generated\"/>" +
                "            <pic:cNvPicPr/>" +
                "         </pic:nvPicPr>" +
                "         <pic:blipFill>" +
                "            <a:blip r:embed=\"" + blipId + "\" xmlns:r=\"http://schemas.openxmlformats.org/officeDocument/2006/relationships\"/>" +
                "            <a:stretch>" +
                "               <a:fillRect/>" +
                "            </a:stretch>" +
                "         </pic:blipFill>" +
                "         <pic:spPr>" +
                "            <a:xfrm>" +
                "               <a:off x=\"0\" y=\"0\"/>" +
                "               <a:ext cx=\"" + width + "\" cy=\"" + height + "\"/>" +
                "            </a:xfrm>" +
                "            <a:prstGeom prst=\"rect\">" +
                "               <a:avLst/>" +
                "            </a:prstGeom>" +
                "           <a:ln w=\"3175\">"+
                "               <a:solidFill>"+
                "                   <a:schemeClr val=\"tx1\"/>"+
                "               </a:solidFill>"+
                "           </a:ln>"+
                "<a:schemeClr val=\"tx1\"/>"+
                "         </pic:spPr>" +
                "      </pic:pic>" +
                "   </a:graphicData>" +
                "</a:graphic>";

        XmlToken xmlToken = null;
        try {
            xmlToken = XmlToken.Factory.parse(picXml);
        } catch(XmlException xe) {
            xe.printStackTrace();
        }
        CTPositiveSize2D extent = null;
        CTNonVisualDrawingProps docPr = null;
        CTInline inline = run.getCTR().addNewDrawing().addNewInline();
        inline.set(xmlToken);
        inline.setDistT(0);
        inline.setDistB(0);
        inline.setDistL(0);
        inline.setDistR(0);
        extent = inline.addNewExtent();
        docPr = inline.addNewDocPr();

        extent.setCx(width);
        extent.setCy(height);

        docPr.setId(id);
        docPr.setName("Picture " + id);
        docPr.setDescr("Generated");
    }

//    public static void main(String[] args) throws IOException, InvalidFormatException {
//		String doc_path = "E:\\apache-tomcat-8.0.30\\webapps\\jeefh\\fh\\doc\\pageoffice\\c3f13d99bb1e455fb585691271a9d729.doc";
//        String doc_path = "D:\\11.docx";
//        String img_path = "E:\\apache-tomcat-8.0.30\\webapps\\jeefh\\fh\\doc\\pageoffice\\img\\jsk.png";
//        new CustomXWPFDocument().runText("PO_user_name1",doc_path, "d");//Bookmark replacement content
//        new CustomXWPFDocument().runImg("PO_user_name1",doc_path, img_path, true, 100, 100, 0, 0);//Bookmark replacement picture
//        // new CustomXWPFDocument().runText("PO_user_name2",doc_path, "bb");//Bookmark replacement content
//        // new CustomXWPFDocument().runImg("PO_user_name2",doc_path, img_path, true, 100, 100, 80, 0);//Bookmark replace picture
//        // new CustomXWPFDocument().runText("PO_user_name3",doc_path, "cc");//Bookmark replacement content
//        // new CustomXWPFDocument().runImg("PO_user_name3",doc_path, img_path, true, 100, 100, 160, 0);//Bookmark replace picture
//    }

    /**
     * @param name book signature
     * @param doc_path word path
     * @param text replaced content
     */
    public void runText(String name,String doc_path,String text) {
        CustomXWPFDocument document = null;
        try {
            document = new CustomXWPFDocument(new FileInputStream(doc_path));
            Iterator<XWPFTable> it = document.getTablesIterator();//Get all tables
            boolean flag = false;
            while(it.hasNext()){
                XWPFTable table = it.next();
                List<XWPFTableRow> rows = table.getRows();//each row of data
                for (XWPFTableRow row : rows) {
                    List<XWPFTableCell> cells = row.getTableCells();//Data in each column
                    for (XWPFTableCell cell : cells) {
                        List<XWPFParagraph> paragraphs = cell.getParagraphs();
                        flag = document.setText(document, paragraphs,name,text);
                        if (flag) {
                            break;
                        }
                    }
                    if (flag) {
                        break;
                    }
                }
                if (flag) {
                    break;
                }
            }
            List<XWPFParagraph> list = document.getParagraphs();//Get all paragraphs
            if (!flag) {//The bookmark you need is not found in the table, continue to search in the paragraph
                document.setText(document, list,name,text);
            }
            FileOutputStream fos = new FileOutputStream(doc_path);
            document.write(fos);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (InvalidFormatException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param document
     * @param paragraphs all paragraphs
     * @param name book signature
     * @param text replaced content
     * @return boolean
     * @throws InvalidFormatException
     * @throws FileNotFoundException
     */
    private boolean setText(CustomXWPFDocument document, List<XWPFParagraph> paragraphs,String name,String text) throws InvalidFormatException, FileNotFoundException {
        boolean flag = false;
        for (XWPFParagraph paragraph: paragraphs) {//traverse paragraphs
            List<CTBookmark> bookmarkStartList = paragraph.getCTP().getBookmarkStartList();//All bookmarks in the current paragraph
            for (CTBookmark bookmark: bookmarkStartList) {//traverse all bookmarks in the current paragraph
                String bookmarkName = bookmark.getName();
                if (name.equals(bookmarkName)) {//Find the bookmark you need
                    XWPFRun run = paragraph.createRun();//Create run
                    run.setText(text);
                    return true;
                }
            }
        }
        return flag;
    }

    /**
     * @param name book signature
     * @param doc_path word path
     * @param img_path image path
     * @param behindDoc whether to float above the text true is to float, false is not to float
     * @param width image width
     * @param height Image height
     * @param leftOffset Image horizontal offset: negative numbers to the left, positive numbers to the right
     * @param topOffset image vertical offset: negative numbers up, positive numbers down
     */
    public void runImg(String name,String doc_path,String img_path,boolean behindDoc,int width, int height,int leftOffset, int topOffset) {
        CustomXWPFDocument document = null;
        try {
            document = new CustomXWPFDocument(new FileInputStream(doc_path));
            Iterator<XWPFTable> it = document.getTablesIterator();//Get all tables
            boolean flag = false;
            while(it.hasNext()){
                XWPFTable table = it.next();
                List<XWPFTableRow> rows = table.getRows();//each row of data
                for (XWPFTableRow row : rows) {
                    List<XWPFTableCell> cells = row.getTableCells();//Data in each column
                    for (XWPFTableCell cell : cells) {
                        List<XWPFParagraph> paragraphs = cell.getParagraphs();
                        flag = document.setPicture(document, paragraphs,name,img_path,behindDoc,width,height,leftOffset,topOffset);
                        if (flag) {
                            break;
                        }
                    }
                    if (flag) {
                        break;
                    }
                }
                if (flag) {
                    break;
                }
            }
            List<XWPFParagraph> list = document.getParagraphs();//Get all paragraphs
            if (!flag) {//The bookmark you need is not found in the table, continue to search in the paragraph
                document.setPicture(document, list,name,img_path,behindDoc,width,height,leftOffset,topOffset);
            }
            int i = doc_path.indexOf(".");
            doc_path = doc_path.substring(0, i)+"_edited.docx";
            FileOutputStream fos = new FileOutputStream(doc_path);
            document.write(fos);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (InvalidFormatException e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param document
     * @param paragraphs all paragraphs
     * @param name book signature
     * @param img_path image path
     * @param behindDoc whether to float above the text true is to float, false is not to float
     * @param width image width
     * @param height Image height
     * @param leftOffset Image horizontal offset: negative numbers to the left, positive numbers to the right
     * @param topOffset image vertical offset: negative numbers up, positive numbers down
     * @return boolean
     * @throws InvalidFormatException
     * @throws FileNotFoundException
     */
    private boolean setPicture(CustomXWPFDocument document, List<XWPFParagraph> paragraphs,String name,String img_path,boolean behindDoc,int width, int height,
                               int leftOffset, int topOffset) throws InvalidFormatException, FileNotFoundException {
        boolean flag = false;
        for (XWPFParagraph paragraph: paragraphs) {//traverse paragraphs
            List<CTBookmark> bookmarkStartList = paragraph.getCTP().getBookmarkStartList();//All bookmarks in the current paragraph
            for (CTBookmark bookmark: bookmarkStartList) {//traverse all bookmarks in the current paragraph
                String bookmarkName = bookmark.getName();
                if (name.equals(bookmarkName)) {//Find the bookmark you need
                    XWPFRun run = paragraph.createRun();//Create run
                    String picId = document.addPictureData(new FileInputStream(img_path), XWPFDocument.PICTURE_TYPE_PNG);
                    document.addPictureToRun(run, picId, document.getNextPicNameNumber(XWPFDocument.PICTURE_TYPE_PNG), width, height);//Add the picture to the created run
                    //Set the picture to float above the text
                    if (behindDoc) {
                        CTDrawing drawing = run.getCTR().getDrawingArray(0);
                        CTGraphicalObject graphicalobject = drawing.getInlineArray(0).getGraphic();
                        //Get the newly inserted picture, replace and add CTAnchor, set floating attribute, delete inline attribute
//                        CTAnchor anchor = getAnchorWithGraphic(graphicalobject, "11",
//                                Units.toEMU(width), Units.toEMU(height),//image size
//                                Units.toEMU(leftOffset), Units.toEMU(topOffset));//The offset position relative to the current paragraph position, left and right offset: negative numbers to the left, positive numbers to the right, up and down offset: negative numbers up, positive numbers down
                        CTAnchor anchor = getAnchorWithGraphic(graphicalobject, "11",
                                width, height,//image size
                                Units.toEMU(leftOffset), Units.toEMU(topOffset));
                        drawing.setAnchorArray(new CTAnchor[]{anchor});//Add floating attributes
                        drawing.removeInline(0);//Remove inline attributes
                    }
                    return true;
                }
            }
        }
        return flag;
    }

    /**
     * @param ctGraphicalObject image data
     * @param deskFileName picture description
     * @param width wide
     * @param height high
     * @param leftOffset horizontal offset: negative numbers to the left, positive numbers to the right
     * @param topOffset vertical offset: negative numbers up, positive numbers down
     * @return CTAnchor
     * @throws Exception
     */
    public static CTAnchor getAnchorWithGraphic(CTGraphicalObject ctGraphicalObject,
                                                String deskFileName, int width, int height,
                                                int leftOffset, int topOffset) {
        //The setting floating on the text is mainly that the behindDoc attribute under the anchor tag is set to 0, and an empty tag of <wp:wrapNone/> is added at the same time.
        if (StringUtils.isBlank(deskFileName)) {
            deskFileName = new Random().nextInt(999) + "";//Description cannot be empty, assign a random number
        }
        String anchorXML =
                "<wp:anchor xmlns:wp=\"http://schemas.openxmlformats.org/drawingml/2006/wordprocessingDrawing\" "
                        + "simplePos=\"0\" relativeHeight=\"0\" behindDoc=\"0\" locked=\"0\" layoutInCell=\"1\" allowOverlap=\"1\">"
                        + "<wp:simplePos x=\"0\" y=\"0\"/>"
                        + "<wp:positionH relativeFrom=\"column\">"
                        + "<wp:posOffset>" + leftOffset + "</wp:posOffset>"
                        + "</wp:positionH>"
                        + "<wp:positionV relativeFrom=\"paragraph\">"
                        + "<wp:posOffset>" + topOffset + "</wp:posOffset>" +
                        "</wp:positionV>"
                        + "<wp:extent cx=\"" + width + "\" cy=\"" + height + "\"/>"
                        + "<wp:effectExtent l=\"0\" t=\"0\" r=\"0\" b=\"0\"/>"
                        + "<wp:wrapNone/>"
                        + "<wp:docPr id=\"1\" name=\"Drawing 0\" descr=\"" + deskFileName + "\"/><wp:cNvGraphicFramePr/>"
                        + "</wp:anchor>";
        CTDrawing drawing = null;
        try {
            drawing = CTDrawing.Factory.parse(anchorXML);
        } catch (XmlException e) {
            e.printStackTrace();
        }
        CTAnchor anchor = drawing.getAnchorArray(0);
        anchor.setGraphic(ctGraphicalObject);
        return anchor;
    }

}
