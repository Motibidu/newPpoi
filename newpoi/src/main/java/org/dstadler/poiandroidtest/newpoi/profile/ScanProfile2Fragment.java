package org.dstadler.poiandroidtest.newpoi.profile;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import org.dstadler.poiandroidtest.newpoi.R;
import org.dstadler.poiandroidtest.newpoi.cls.Constant;
import org.dstadler.poiandroidtest.newpoi.cls.PreferenceManager;

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

    private String result;

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

        edit_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                PreferenceManager.setString(mContext,"edit_name", edit_name.getText().toString());
            }
        });
        edit_engName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                PreferenceManager.setString(mContext,"edit_engName", edit_engName.getText().toString());
            }
        });
        edit_chName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                PreferenceManager.setString(mContext,"edit_chName", edit_chName.getText().toString());
            }
        });
        edit_rrn.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                PreferenceManager.setString(mContext,"edit_rrn", edit_rrn.getText().toString());
            }
        });

        edit_phoneNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                PreferenceManager.setString(mContext,"edit_phoneNum", edit_phoneNum.getText().toString());
            }
        });
        edit_email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                PreferenceManager.setString(mContext,"edit_email", edit_email.getText().toString());
            }
        });
        edit_addr.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                PreferenceManager.setString(mContext,"edit_addr", edit_addr.getText().toString());
            }
        });


        //ImageButton
        imageButton_name = v.findViewById(R.id.imagebutton_name);
        imageButton_engName = v.findViewById(R.id.imagebutton_engName);
        imageButton_chName = v.findViewById(R.id.imagebutton_chName);
        imageButton_rrn = v.findViewById(R.id.imagebutton_rrn);
        imageButton_phoneNum = v.findViewById(R.id.imagebutton_phoneNum);
        imageButton_email = v.findViewById(R.id.imagebutton_email);
        imageButton_addr = v.findViewById(R.id.imagebutton_addr);




        imageButton_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                PreferenceManager.setString(mContext,"edit_name", setNameMenu(view, edit_name, name));
                setNameMenu(view, edit_name, name);
            }
        });
        imageButton_engName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                PreferenceManager.setString(mContext,"edit_engName", setNameMenu(view, edit_engName, engName));
                setNameMenu(view, edit_engName, engName);
            }
        });
        imageButton_chName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                PreferenceManager.setString(mContext,"edit_chName", setNameMenu(view, edit_chName, chName));
                setNameMenu(view, edit_chName, chName);
            }
        });
        imageButton_rrn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                PreferenceManager.setString(mContext,"edit_rrn", setNameMenu(view, edit_rrn, rnn));
                setNameMenu(view, edit_rrn, rnn);
            }
        });

        imageButton_phoneNum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                PreferenceManager.setString(mContext,"edit_phoneNum", setNameMenu(view, edit_phoneNum, phoneNum));
                setNameMenu(view, edit_phoneNum, phoneNum);
            }
        });
        imageButton_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                PreferenceManager.setString(mContext,"edit_email", setNameMenu(view, edit_email, email));
                setNameMenu(view, edit_email, email);
            }
        });
        imageButton_addr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                PreferenceManager.setString(mContext,"edit_addr", setNameMenu(view, edit_addr, addr));
                setNameMenu(view, edit_addr, addr);
            }
        });

        if (name.size() != 0) {
            edit_name.setText(name.get(name.size() - 1));
            PreferenceManager.setString(mContext,"edit_name", name.get(name.size() - 1));
            Log.d(TAG, "edit_name : " + name.get(name.size() - 1));
        } else {
            edit_name.setText("");
            PreferenceManager.setString(mContext,"edit_name", "");
        }
        if (engName.size() != 0) {
            edit_engName.setText(engName.get(engName.size() - 1));
            PreferenceManager.setString(mContext,"edit_engName", engName.get(engName.size() - 1));
            Log.d(TAG, engName.get(engName.size() - 1));
        } else {
            edit_engName.setText("");
            PreferenceManager.setString(mContext,"edit_engName", "");
        }
        if (chName.size() != 0) {
            edit_chName.setText(chName.get(chName.size() - 1));
            PreferenceManager.setString(mContext,"edit_chName", chName.get(chName.size() - 1));
            Log.d(TAG, chName.get(chName.size() - 1));
        } else {
            edit_chName.setText("");
            PreferenceManager.setString(mContext,"edit_chName", "");
        }

        if (rnn.size() != 0) {
            edit_rrn.setText(rnn.get(rnn.size() - 1));
            PreferenceManager.setString(mContext,"edit_rrn", rnn.get(rnn.size() - 1));
            Log.d(TAG, rnn.get(rnn.size() - 1));
        } else {
            edit_rrn.setText("");
            PreferenceManager.setString(mContext,"edit_rrn", "");
        }
        //when getAge is empty
        if(getAge().length() == 0 || getAge() == null){
            edit_age.setText("");
            PreferenceManager.setString(mContext,"edit_age", "");
        }else{ //when getAge exists
            edit_age.setText(getAge());
            PreferenceManager.setString(mContext,"edit_age", getAge());
            Log.d(TAG, getAge());
        }
        if (phoneNum.size() != 0) {
            edit_phoneNum.setText(phoneNum.get(phoneNum.size() - 1));
            PreferenceManager.setString(mContext,"edit_phoneNum", phoneNum.get(phoneNum.size() - 1));
            Log.d(TAG, phoneNum.get(phoneNum.size() - 1));
        } else {
            edit_phoneNum.setText("");
            PreferenceManager.setString(mContext,"edit_phoneNum", "");
        }

        if (email.size() != 0) {
            edit_email.setText(email.get(email.size() - 1));
            PreferenceManager.setString(mContext,"edit_email", email.get(email.size() - 1));
            Log.d(TAG, email.get(email.size() - 1));
        } else {
            edit_email.setText("");
            PreferenceManager.setString(mContext,"edit_email", "");
        }

        if (addr.size() != 0) {
            edit_addr.setText(addr.get(addr.size() - 1));
            PreferenceManager.setString(mContext,"edit_addr", addr.get(addr.size() - 1));
            Log.d(TAG, addr.get(addr.size() - 1));
        } else {
            edit_addr.setText("");
            PreferenceManager.setString(mContext,"edit_addr", "");
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

    private String setNameMenu(View view, EditText editText, List<String> list) {
        PopupMenu menu = new PopupMenu(mContext, view);
        final String[] r = new String[1];
        for (int i = 0; i < list.size(); i++) {
            menu.getMenu().add(Menu.NONE, i, i, list.get(i));
        }
        menu.show();
        if (view.getId() == R.id.imagebutton_rrn) {
            menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    editText.setText(menuItem.getTitle());
                    r[0] = (String) menuItem.getTitle();
                    setAge();
                    return true;
                }
            });
        } else {
            menu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem menuItem) {
                    editText.setText(menuItem.getTitle());
                    r[0] = (String) menuItem.getTitle();
                    return true;
                }
            });
        }
        return r[0];
    }

}