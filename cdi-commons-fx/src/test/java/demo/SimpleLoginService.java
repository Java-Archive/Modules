package demo;

public class SimpleLoginService implements LoginService {
	@Override
	public String login(String login, String password) {
		if (password != null && password.trim().length() > 0) {
			return String.format("%1$s logged in successfully", login);
		}
		return String.format("%1$s failed to login", login);
	}
}
