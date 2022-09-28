package org.dstadler.poiandroidtest.newpoi.cls;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class customMatcher {
    Pattern pat;
    Matcher mat;
    String splitStr;

    List<String> rnn = new ArrayList<String>();

    // name_regx 테스트
    // 후보군 첫번째에 로그인에서 이름을 불러온다.
    String name_regx
            = "^(김|이|박|최|정|강|조|윤|장|임|한|오|서|신|권|황|안|송|류|전|홍|고|문|양|손|배|조|백|허|유|남|심|노|정|하|곽|성|차|주|우|구|신|임|라|전|민|유|진|지|엄)[가-힣]{1,3}";


    // rnn_regx1 테스트
    //
    String rnn_regx1 //981109-1236412
            = "\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|[3][01])\\-[1-4][0-9]{6}";

    // rnn_regx2 테스트
    // 1998년 11월 09일 (만 23세)
    // jack981109@naver.com
    String rnn_regx2 //98(.| |년)11(.| |월)09(.| |일)
            = "(6|7|8|9)\\d(\\.|\\s|년)(\\s)?(0?[1-9]|1[0-2])(\\.|\\s|월)(\\s)?(0[1-9]|[12][0-9]|[3][01])(일)?(\\s)?";

    // rnn_regx3 테스트
    // 1987년 01월 01일 (만 00세)
    String rnn_regx3
            = "(19|20)\\d{2}(\\.|\\s|년)?(\\s)?(0?[1-9]|1[0-2])(\\.|\\s|월)?(\\s)?(0[1-9]|[12][0-9]|[3][01])(일)?(\\s)?";



    //phoneNum_regx 테스트
    //010-6230-1825
    //010,1234.1234
    //•010,1234.1234
    String phoneNum_regx
            = "01(?:0|1|[6-9])[\\.|\\-|\\,]?(\\d{3}|\\d{4})[\\.|\\-|\\,]?(\\d{4})";

    //url_regx 테스트
    //
    String url_regx //https://github.com/Motibidu
            = "(https?):\\/\\/([^:\\/\\s]+)(:([^\\/]*))?((\\/[^\\s/\\/]+)*)?\\/?([^#\\s\\?]*)(\\?([^#\\s]*))?(#(\\w*))?";

    // email 테스트
    //
    String email_regx //jack981109@naver.com
            = "(.*)[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}(.*)";

    //addrregx 테스트
    //
    String addr_regx
            = "(([가-힣A-Za-z·\\d~\\-\\.]{2,}(로|길|대로).[\\d|\\-\\d]+)|([가-힣A-Za-z·\\d~\\-\\.]+(읍|면|동|리)\\s)[\\d]+)";


    //schl_regx 테스트
    //
    String schl_regx //...학교 | ...대학 | ...학원
            = "[가-힣]*(학교|대학|학원)";

    //period 테스트
    //
    String period //19?? | 20??
            = "(19|20)[0-9]{2}";






    public customMatcher(){super();}

    public void set_splitStr(String str){
        splitStr = str;
    }

    public void set_name(){
        pat = Pattern.compile(name_regx);
        mat = pat.matcher(splitStr);
    }

    public void set_rnn1(){
        pat = Pattern.compile(rnn_regx1);
        mat = pat.matcher(splitStr);
    }
    public void set_rnn2(){
        pat = Pattern.compile(rnn_regx2);
        mat = pat.matcher(splitStr);
    }
    public void set_rnn3(){
        pat = Pattern.compile(rnn_regx3);
        mat = pat.matcher(splitStr);
    }

    public void set_phoneNum(){
        pat = Pattern.compile(phoneNum_regx);
        mat = pat.matcher(splitStr);
    }
    public void set_email(){
        pat = Pattern.compile(email_regx);
        mat = pat.matcher(splitStr);
    }
    public void set_addr(){
        pat = Pattern.compile(addr_regx);
        mat = pat.matcher(splitStr);
    }
    public boolean find(){
        return mat.find();
    }
    public boolean matches(){
        return mat.matches();
    }
    public String group(){
        return mat.group();
    }


}
