package in.museon.assignment.ui.dialog;

public class DialogParams {

	private int dialogId;
	private String title = "ALERT";
	private String message = "No description available";
	private String positive = "YES";
	private String negative = "NO";
	private String neutral = "CANCEL";
	private boolean cancelable = false;
	private DialogType dgType = DialogType.DG_POS_NEG;

	public int getDialogId() {
		return dialogId;
	}

	public void setDialogId(int dialogId) {
		this.dialogId = dialogId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPositive() {
		return positive;
	}

	public void setPositive(String positive) {
		this.positive = positive;
	}

	public String getNegative() {
		return negative;
	}

	public void setNegative(String negative) {
		this.negative = negative;
	}

	public String getNeutral() {
		return neutral;
	}

	public void setNeutral(String neutral) {
		this.neutral = neutral;
	}

	public boolean isCancelable() {
		return cancelable;
	}

	public void setCancelable(boolean cancelable) {
		this.cancelable = cancelable;
	}

	public DialogType getDgType() {
		return dgType;
	}

	public void setDgType(DialogType dgType) {
		this.dgType = dgType;
	}

	public static class Builder {
		private int dialogId;
		private String title = "ALERT";
		private String message = "Default message";
		private String positive = "YES";
		private String negative = "NO";
		private String neutral = "CANCEL";
		private boolean cancelable = false;


		private DialogType dgType = DialogType.DG_POS_NEG;

		public Builder dialogId(int dialogId) {
			this.dialogId = dialogId;
			return this;
		}

		public Builder title(String title) {
			this.title = title;
			return this;
		}

		public Builder message(String message) {
			this.message = message;
			return this;
		}

		public Builder positive(String positive) {
			this.positive = positive;
			return this;
		}

		public Builder negative(String negative) {
			this.negative = negative;
			return this;
		}

		public Builder nutral(String nutral) {
			this.neutral = nutral;
			return this;
		}

		public Builder cancelable(Boolean cancelable) {
			this.cancelable = cancelable;
			return this;
		}

		public Builder dgType(DialogType dgType) {
			this.dgType = dgType;
			return this;
		}

		public DialogParams build() {
			return new DialogParams(this);
		}
	}

	private DialogParams(Builder builder) {
		this.dialogId = builder.dialogId;
		this.title = builder.title;
		this.message = builder.message;
		this.positive = builder.positive;
		this.negative = builder.negative;
		this.neutral = builder.neutral;
		this.cancelable = builder.cancelable;
		this.dgType = builder.dgType;
	}
}
