package Exception;
public class InvalidPasswordException extends Exception {
	  
    int passwordConditionViolated = 0;
  
    public InvalidPasswordException(int conditionViolated)
    {
        super("Invalid Password: ");
        passwordConditionViolated = conditionViolated;
    }
  
    public String printMessage()
    {
        // Call constructor of parent Exception
        // according to the condition violated
        switch (passwordConditionViolated) {
  
        // Password length should be
        // between 8 to 15 characters
        case 1:
            return ("Mật Khẩu phải"
                    + " từ 8 đến 15 kí tự");
  
        // Password should not contain any space
        case 2:
            return ("Mật Khẩu không được"
                    + " chứa khoảng trắng");
  
        // Password should contain// at least one digit(0-9)
        case 3:
            return ("Mật khẩu phải chứa"
                    + " 1 chữ số");
  
        // Password should contain at least
        // one special character ( @, #, %, &, !, $ )
        case 4:
            return ("Mật khẩu phải chứa "
                    + "1 kí tự đặc biệt");
  
        // Password should contain at least
        // one uppercase letter(A-Z)
        case 5:
            return ("Mật khẩu phải chứa"
                    + "1 chữ viết hoa");
  
        // Password should contain at least
        // one lowercase letter(a-z)
        case 6:
            return ("Mật khẩu phải chứa"
                    + "1 chữ thường");
        }
  
        return ("");
    }
}
  