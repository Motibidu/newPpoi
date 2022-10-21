package org.dstadler.poiandroidtest.newpoi.profile;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;

import androidx.fragment.app.Fragment;

import org.dstadler.poiandroidtest.newpoi.R;
import org.dstadler.poiandroidtest.newpoi.cls.Constant;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;


public class ScanProfile2Fragment extends Fragment{
    private Context mContext;
    private final String TAG = "SCANPROFILE2FRAGMENT";
    private Map<String, ArrayList<String>> map;

    private View v;
    private ScanActivity ScanActivity;

    private EditText edit_name, edit_engName, edit_chName, edit_rrn, edit_phoneNum,
            edit_addr, edit_email, edit_age;
    private ImageButton imageButton_name, imageButton_engName, imageButton_chName, imageButton_rrn, imageButton_phoneNum,
            imageButton_addr, imageButton_email;

    private ArrayList<String> name = new ArrayList<String>();
    private ArrayList<String> engName = new ArrayList<String>();
    private ArrayList<String> chName = new ArrayList<String>();
    private ArrayList<String> rnn = new ArrayList<String>();
    private ArrayList<String> email = new ArrayList<String>();
    private ArrayList<String> addr = new ArrayList<String>();
    private ArrayList<String> phoneNum = new ArrayList<String>();
    private ArrayList<String> age = new ArrayList<String>();

    private buttonCompleteListener buttonCompleteListener;

    public interface buttonCompleteListener {
        void buttonCompleteListen();
    }


    public ScanProfile2Fragment() {
    }

    public ScanProfile2Fragment(Map<String, ArrayList<String>> map) {
        name = map.get("name");
        engName = map.get("engName");
        chName = map.get("chName");
        rnn = map.get("rnn");
        email = map.get("email");
        addr = map.get("addr");
        phoneNum = map.get("phoneNum");

        if(name.isEmpty()){
            Constant.scanInfo.put("name", "");
        }else{
            Constant.scanInfo.put("name", name.get(name.size() - 1));
        }
        if(engName.isEmpty()){
            Constant.scanInfo.put("engName", "");
        }else{
            Constant.scanInfo.put("engName", engName.get(engName.size() - 1));
        }
        if(chName.isEmpty()){
            Constant.scanInfo.put("chName", "");
        }else{
            Constant.scanInfo.put("chName", chName.get(chName.size() - 1));
        }
        if(rnn.isEmpty()){
            Constant.scanInfo.put("rnn", "");
        }else{
            Constant.scanInfo.put("rnn", rnn.get(rnn.size() - 1));
        }
        if(email.isEmpty()){
            Constant.scanInfo.put("email", "");
        }else{
            Constant.scanInfo.put("email", email.get(email.size() - 1));
        }
        if(addr.isEmpty()){
            Constant.scanInfo.put("addr", "");
        }else{
            Constant.scanInfo.put("addr", addr.get(addr.size() - 1));
        }
        if(phoneNum.isEmpty()){
            Constant.scanInfo.put("phoneNum", "");
        }else{
            Constant.scanInfo.put("phoneNum", phoneNum.get(phoneNum.size() - 1));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_scan_profile, container, false);

        mContext = getContext();

        //EditText
        edit_name = v.findViewById(R.id.edit_name);
        edit_engName = v.findViewById(R.id.edit_engName);
        edit_chName = v.findViewById(R.id.edit_chName);
        edit_rrn = v.findViewById(R.id.edit_rrn);
        edit_age = v.findViewById(R.id.edit_age);
        edit_phoneNum = v.findViewById(R.id.edit_phoneNum);
        edit_email = v.findViewById(R.id.edit_email);
        edit_addr = v.findViewById(R.id.edit_addr);

        //ImageButton
        imageButton_name = v.findViewById(R.id.imagebutton_name);
        imageButton_engName = v.findViewById(R.id.imagebutton_engName);
        imageButton_chName = v.findViewById(R.id.imagebutton_chName);
        imageButton_rrn = v.findViewById(R.id.imagebutton_rrn);
        imageButton_phoneNum = v.findViewById(R.id.imagebutton_phoneNum);
        imageButton_email = v.findViewById(R.id.imagebutton_email);
        imageButton_addr = v.findViewById(R.id.imagebutton_addr);


        if(getAge().length() == 0 || getAge() == null){
            Constant.scanInfo.put("age", "");
        }else{
            Constant.scanInfo.put("age", getAge());
        }

        imageButton_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setNameMenu(view, edit_name, name);
            }
        });
        imageButton_engName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setNameMenu(view, edit_engName, engName);
            }
        });
        imageButton_chName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setNameMenu(view, edit_chName, chName);
            }
        });
        imageButton_rrn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setNameMenu(view, edit_rrn, rnn);
            }
        });

        imageButton_phoneNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setNameMenu(view, edit_phoneNum, phoneNum);
            }
        });
        imageButton_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setNameMenu(view, edit_email, email);
            }
        });
        imageButton_addr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setNameMenu(view, edit_addr, addr);
            }
        });

        if (name.size() != 0) {
            edit_name.setText(name.get(name.size() - 1));
            Log.d(TAG, "edit_name : " + name.get(name.size() - 1));
        } else {
            edit_name.setText("");
        }
        if (engName.size() != 0) {
            edit_engName.setText(engName.get(engName.size() - 1));
            Log.d(TAG, engName.get(engName.size() - 1));
        } else {
            edit_engName.setText("");
        }
        if (chName.size() != 0) {
            edit_chName.setText(chName.get(chName.size() - 1));
            Log.d(TAG, chName.get(chName.size() - 1));
        } else {
            edit_chName.setText("");
        }

        if (rnn.size() != 0) {
            edit_rrn.setText(rnn.get(rnn.size() - 1));
            Log.d(TAG, rnn.get(rnn.size() - 1));
        } else {
            edit_rrn.setText("");
        }

        if (phoneNum.size() != 0) {
            edit_phoneNum.setText(phoneNum.get(phoneNum.size() - 1));
            Log.d(TAG, phoneNum.get(phoneNum.size() - 1));
        } else {
            edit_phoneNum.setText("");
        }

        if (email.size() != 0) {
            edit_email.setText(email.get(email.size() - 1));
            Log.d(TAG, email.get(email.size() - 1));
        } else {
            edit_email.setText("");
        }

        if (addr.size() != 0) {
            edit_addr.setText(addr.get(addr.size() - 1));
            Log.d(TAG, addr.get(addr.size() - 1));
        } else {
            edit_addr.setText("");
        }
        setAge();


        return v;
    }

    private void setAge() {
        String prrn = edit_rrn.getText().toString();
        int year = Calendar.getInstance().get(Calendar.YEAR);
        String[] splitprrn = new String[3];

        if (prrn.length() > 0) {
            //String[] splitprrn = prrn.split(".");
            if (prrn.contains(".")) {
                splitprrn = prrn.split("\\.");
                Log.d(TAG, "splitrrn[0] : " + splitprrn[0]);
            } else if (prrn.contains("년")) {
                splitprrn = prrn.split("년");
                Log.d(TAG, "splitrrn[0] : " + splitprrn[0]);
            } else if (prrn.contains(" ")) {
                splitprrn = prrn.split("\\s");
                Log.d(TAG, "splitrrn[0] : " + splitprrn[0]);
            } else if (prrn.contains(",")) {
                splitprrn = prrn.split(",");
                Log.d(TAG, "splitrrn[0] : " + splitprrn[0]);
            }

            if (splitprrn[0].length() == 2) {
                String firstChar = Character.toString(splitprrn[0].charAt(0));
                Log.d(TAG, "firshChar : " + firstChar);
                //68, 71, 86, 92
                if (firstChar.equals("6") || firstChar.equals("7") || firstChar.equals("8") || firstChar.equals("9")) {
                    Log.d(TAG, "condition1 : " + Integer.toString(year - Integer.parseInt(splitprrn[0]) - 1899));
                    edit_age.setText(Integer.toString(year - Integer.parseInt(splitprrn[0]) - 1899));
                }
                //05, 12, 13, 21
                else if (firstChar.equals("0") || firstChar.equals("1") | firstChar.equals("2")) {
                    Log.d(TAG, "condition2 : " + Integer.toString(year - Integer.parseInt(splitprrn[0]) - 1999));
                    edit_age.setText(Integer.toString(year - Integer.parseInt(splitprrn[0]) - 1999));
                }
            } else if (splitprrn[0].length() == 4) {
                Log.d(TAG, "condition3 : " + Integer.toString(year - Integer.parseInt(splitprrn[0]) + 1));
                edit_age.setText(Integer.toString(year - Integer.parseInt(splitprrn[0]) + 1));
            }
        }
    }

    private String getAge() {
        String prrn = edit_rrn.getText().toString();
        int year = Calendar.getInstance().get(Calendar.YEAR);
        String[] splitprrn = new String[3];

        if (prrn.length() > 0) {
            //String[] splitprrn = prrn.split(".");
            if (prrn.contains(".")) {
                splitprrn = prrn.split("\\.");
                Log.d(TAG, "splitrrn[0] : " + splitprrn[0]);
            } else if (prrn.contains("년")) {
                splitprrn = prrn.split("년");
                Log.d(TAG, "splitrrn[0] : " + splitprrn[0]);
            } else if (prrn.contains(" ")) {
                splitprrn = prrn.split("\\s");
                Log.d(TAG, "splitrrn[0] : " + splitprrn[0]);
            } else if (prrn.contains(",")) {
                splitprrn = prrn.split(",");
                Log.d(TAG, "splitrrn[0] : " + splitprrn[0]);
            }

            if (splitprrn[0].length() == 2) {
                String firstChar = Character.toString(splitprrn[0].charAt(0));
                Log.d(TAG, "firshChar : " + firstChar);
                //68, 71, 86, 92
                if (firstChar.equals("6") || firstChar.equals("7") || firstChar.equals("8") || firstChar.equals("9")) {
                    Log.d(TAG, "condition1 : " + Integer.toString(year - Integer.parseInt(splitprrn[0]) - 1899));
                    return Integer.toString(year - Integer.parseInt(splitprrn[0]) - 1899);
                }
                //05, 12, 13, 21
                else if (firstChar.equals("0") || firstChar.equals("1") | firstChar.equals("2")) {
                    Log.d(TAG, "condition2 : " + Integer.toString(year - Integer.parseInt(splitprrn[0]) - 1999));
                    return Integer.toString(year - Integer.parseInt(splitprrn[0]) - 1999);
                }
            } else if (splitprrn[0].length() == 4) {
                Log.d(TAG, "condition3 : " + Integer.toString(year - Integer.parseInt(splitprrn[0]) + 1));
                return Integer.toString(year - Integer.parseInt(splitprrn[0]) + 1);
            }
        }

        return prrn;
    }

    private void setNameMenu(View view, EditText editText, List<String> list) {
        PopupMenu menu = new PopupMenu(mContext, view);
        for (int i = 0; i < list.size(); i++) {
            menu.getMenu().add(Menu.NONE, i, i, list.get(i));
        }
        menu.show();
        if (view.getId() == R.id.imagebutton_rrn) {
            menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    editText.setText(menuItem.getTitle());
                    setAge();
                    return true;
                }
            });
        } else {
            menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    editText.setText(menuItem.getTitle());
                    return true;
                }
            });

        }
    }

}