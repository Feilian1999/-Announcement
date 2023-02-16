import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
//序列化的格式以及相關功能
public class PostSerializable implements Serializable {

	private String content = "";
	private boolean isLike = false;
	private Date editTime = new Date();

//	private SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.US);

	public PostSerializable(String content, boolean isLike, Date editTime) {
		this.content = content;
		this.isLike = isLike;
		this.editTime = editTime;
	}

//	public PostSerializable(String content, boolean isLike, String editTimeInString) {
//		this.content = content;
//		this.isLike = isLike;
//		setEditTimeByString(editTimeInString);
//	}

	public void setContent(String newContent) {
		this.content = newContent;
	}

	public void setIsLike(boolean isLike) {
		this.isLike = isLike;
	}

	public void setEditTime(Date newEditTime) {
		this.editTime = newEditTime;
	}

	public String getContent() {
		return this.content;
	}

	public boolean getIsLike() {
		return this.isLike;
	}

	public Date getEditTime() {
		return this.editTime;
	}
	
//	public void setEditTimeByString(String dateString) {
//		try {
//			this.editTime = sdf.parse(dateString);
//		} catch (ParseException e) {
//			System.out.println("Unparseable using " + dateString);
//		}
//	}
//
//	public String getEditTimeInString() {
//		return sdf.format(this.editTime);
//	}

	public String toString() {
		return String.format("isLike: %s%neditTime: %s%ncontent: %s", String.valueOf(isLike), editTime.toString(),
				content);
	}

}
