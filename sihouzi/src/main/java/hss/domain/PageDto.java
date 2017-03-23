package hss.domain;



import java.io.Serializable;

/**
 * Created by ClownMonkey on 2016/5/13.
 * <p>
 * 分页查询数据集合
 */
public class PageDto implements Serializable {

    private static final long serialVersionUID = 1637818817791013129L;

    private Object content;

    public Object getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Object totalPages) {
        this.totalPages = totalPages;
    }

    private Object totalPages;

    public Object getSize() {
        return size;
    }

    public void setSize(Object size) {
        this.size = size;
    }

    private Object size;

    private Object totalElements;

    public PageDto() {
    }

    public Object getContent() {
        return content;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public Object getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Object totalElements) {
        this.totalElements = totalElements;
    }
}
