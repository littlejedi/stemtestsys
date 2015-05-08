package com.testsys.admin.bean;
 
import java.util.ArrayList;
import java.util.List;
 
/**
 * 
 * @author hh
 * 
 */
public class PageHelper
{
    // �ܹ��������
    private int total;
 
    // ÿҳ��ʾ������
    private int pageSize;
 
    // ���ж���ҳ
    private int totalPage;
 
    // ��ǰ�ǵڼ�ҳ
    private int index;
 
    // ���
    private List<?> data;
 
    private List<Page> pagelist = new ArrayList<Page>();
    // ����·��
    private String path = "";
 
    /**
     * ҳ��HTML��Ϣ
     */
    @SuppressWarnings("unused")
    private String pageHTML;
 
    private int startPage; // ��ʼҳ��
 
    private int endPage; // ����ҳ��
 
    private int displayNum = 5; // ��ʾ��ҳ��
 
    /**
     * @return the startPage
     */
    public int getStartPage()
    {
        return startPage;
    }
 
    /**
     * @return the endPage
     */
    public int getEndPage()
    {
        return endPage;
    }
 
    /**
     * @return the path
     */
    public String getPath()
    {
        return path;
    }
 
    public void setPageSize(int pageSize)
    {
        this.pageSize = pageSize;
    }
 
    public void setIndex(int index)
    {
        this.index = index;
    }
 
    /**
     * ����·��ǰ׺��ҳ���һҳindexΪ1
     * 
     * @param path
     *            ��path���в�����ʽ����/aa/article?page=,����/bb/article/list/
     */
    public void setPath(String path)
    {
        this.path = path;
    }
 
    public int getPageSize()
    {
        return pageSize;
    }
 
    public int getTotalPage()
    {
        return (this.total + this.pageSize - 1) / this.pageSize;
    }
 
    public int getIndex()
    {
        return index;
    }
 
    public List<?> getData()
    {
        return data;
    }
 
    public void setData(List<?> data)
    {
        this.data = data;
    }
 
    /**
     * @return the total
     */
    public int getTotal()
    {
        return total;
    }
 
    /**
     * @param total
     *            the total to set
     */
    public void setTotal(int total)
    {
        this.total = total;
    }
    public void generatePageList()
    {
    	this.pagelist.clear();
    	totalPage = getTotalPage();
        
    	 if (totalPage != 0 && pageSize != 0)
         {
         	Page first = new Page();
         	first.setName("首页");
         	first.setLink(path+"1");
         	
         	Page pre = new Page();
         	pre.setName("上一页");
         	pre.setLink(path+(index - 1));
             if (index <= 1)
             {
             	first.setDisabled(1);
             	pre.setDisabled(1);
             }
             else
             {
             	
             }
             this.pagelist.add(first);
         	this.pagelist.add(pre);
             countPages();
             for (int i = startPage; i <= endPage; i++)
             {
             	Page page = new Page();
             	page.setName(i+"");
             	page.setLink(path+i);
                 if (i == index)
                 {
                 	page.setCur(1);
                 }
                 this.pagelist.add(page);
             }
             Page last = new Page();
         	last.setName("末页");
         	last.setLink(path+totalPage);
         	
         	Page next = new Page();
         	next.setName("下一页");
         	next.setLink(path+(index + 1));
             if (index >= totalPage)
             {
             	last.setDisabled(1);
             	next.setDisabled(1);
             }
             else
             {
             	
             	
             	
             }
             this.pagelist.add(next);
         	this.pagelist.add(last);
         }
    }
    public List<Page> getPageList()
    {
    	this.generatePageList();
    	return this.pagelist;
    }
    
 
    /**
     * ������ʼҳ�ͽ���ҳ
     */
    public void countPages()
    {
 
        if (index - displayNum / 2 < 1)
        {
            startPage = 1;
            endPage = displayNum > totalPage ? totalPage : displayNum;
        }
        else if (index + displayNum / 2 > totalPage)
        {
            int n = totalPage - displayNum + 1;
            startPage = n > 0 ? n : 1;
            endPage = totalPage;
        }
        else
        {
            startPage = index - displayNum / 2;
            endPage = startPage + displayNum - 1;
        }
    }
 
    /**
     * @param pageHTML the pageHTML to set
     */
    public void setPageHTML(String pageHTML)
    {
        this.pageHTML = pageHTML;
    }
}