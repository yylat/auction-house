package com.epam.auction.page;

public class Page {

    private PageName pageName;
    private TransferMethod transferMethod;

    public Page() {
    }

    public Page(PageName pageName) {
        this.pageName = pageName;
    }

    public Page(PageName pageName, TransferMethod transferMethod) {
        this.pageName = pageName;
        this.transferMethod = transferMethod;
    }

    public PageName getPageName() {
        return pageName;
    }

    public void setPageName(PageName pageName) {
        this.pageName = pageName;
    }

    public TransferMethod getTransferMethod() {
        return transferMethod;
    }

    public void setTransferMethod(TransferMethod transferMethod) {
        this.transferMethod = transferMethod;
    }

    public String getAddress(){
        return pageName.getAddress();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Page page = (Page) o;

        return (pageName != null ? pageName.equals(page.pageName) : page.pageName == null) && transferMethod == page.transferMethod;
    }

    @Override
    public int hashCode() {
        int result = pageName != null ? pageName.hashCode() : 0;
        result = 31 * result + (transferMethod != null ? transferMethod.hashCode() : 0);
        return result;
    }

}