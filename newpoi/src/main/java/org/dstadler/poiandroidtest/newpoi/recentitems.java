package org.dstadler.poiandroidtest.newpoi;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.appbar.MaterialToolbar;

public class recentitems extends Fragment {
    private View view;
    private MaterialToolbar toolbar;
    private add_screen add_screen;
    private profile_screen profile_screen;
    private FragmentManager fm;
    private FragmentTransaction ft;



//    public void createPdf() throws FileNotFoundException{
//        String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
//        File file = new File(pdfPath, "myPDFa.pdf");
//        OutputStream outputStream = new FileOutputStream(file);
//
//        PdfWriter writer = new PdfWriter(file);
//        PdfDocument pdfDocument = new PdfDocument(writer);
//        Document document = new Document(pdfDocument);
//
//        Paragraph paragraph = new Paragraph("Hello JiHoon Technology");
//
//        document.add(paragraph);
//        document.close();
//    }
//    public void createPdf() throws FileNotFoundException{
//        String pdfPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString();
//        File file = new File(pdfPath, "myPDFa.pdf");
//        OutputStream outputStream = new FileOutputStream(file);
//
//        PdfWriter writer = new PdfWriter(file);
//        PdfDocument pdfDocument = new PdfDocument(writer);
//        Document document = new Document(pdfDocument);
//
//        pdfDocument.setDefaultPageSize(PageSize.A4);
//        document.setMargins(50,30,30,30);
//
//        Paragraph self_introduction = new Paragraph("kkk").setBold().setFontSize(24).setTextAlignment(TextAlignment.CENTER);
//        Paragraph paragraph = new Paragraph("Hello JiHoon Technology");
//
//        float[] width = {100f, 100f};
//        Table table = new Table(width);
//        table.setHorizontalAlignment(HorizontalAlignment.CENTER);
//
//        table.addCell(new Cell().add(new Paragraph("kk:")));
//        table.addCell(new Cell().add(new Paragraph("kk")));
//
//        document.add(self_introduction);
//        document.add(paragraph);
//        document.add(table);
//        document.close();
//    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.recentitems, container, false);

        add_screen = new add_screen();
        profile_screen = new profile_screen();
        toolbar = (MaterialToolbar) view.findViewById(R.id.topAppBar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), profile_screen.class);
                startActivity(intent);
            }
        });
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.add_screen: {
//                        try {
//                            createPdf();
//                            Toast.makeText(getActivity(),"Created",Toast.LENGTH_LONG).show();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                            Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
//                        }
//                        Intent intent = new Intent(getActivity(), add_screen.class);
//                        startActivity(intent);
                        Intent intent = new Intent(getActivity(), add_screen.class);
                        startActivity(intent);
                        break;
                    }
                }
                return false;
            }
        });

        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.top_app_bar_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }



}
