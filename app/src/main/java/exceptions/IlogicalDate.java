package exceptions;

import com.example.e1_t6_mob_2dam.GlobalVariables;
import com.example.e1_t6_mob_2dam.R;

public class IlogicalDate extends Exception {
    public IlogicalDate() {
        super(GlobalVariables.context.getString(R.string.txt_IlogicalDate));
    }
}
