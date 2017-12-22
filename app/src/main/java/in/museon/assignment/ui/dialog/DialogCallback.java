package in.museon.assignment.ui.dialog;

public interface DialogCallback {

	void onButtonPositive(int dialogId);

	void onButtonNegative(int dialogId);

	void onButtonNeutral(int dialogId);
}
