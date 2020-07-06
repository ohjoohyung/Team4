package wwwwwwww.notice.dto;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
public class NoticeDto{
	private int notice_brd_seq;
	private String notice_brd_title;
	private String notice_brd_content;
	private Date notice_brd_date;
	private int notice_brd_view_num;
	
}