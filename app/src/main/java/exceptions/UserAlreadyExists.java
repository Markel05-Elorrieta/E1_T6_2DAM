package exceptions;

import com.example.e1_t6_mob_2dam.GlobalVariables;
import com.example.e1_t6_mob_2dam.R;

public class UserAlreadyExists extends Exception{
    public UserAlreadyExists() {
        super(GlobalVariables.context.getString(R.string.txt_UserAlreadyExists));
    }
}
