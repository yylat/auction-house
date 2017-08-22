package com.epam.auction.controller;

public class PageGuide {

    private String pageAddress;
    private TransferMethod transferMethod;

    public PageGuide() {
    }

    public PageGuide(String pageAddress) {
        this.pageAddress = pageAddress;
    }

    public PageGuide(String pageAddress, TransferMethod transferMethod) {
        this.pageAddress = pageAddress;
        this.transferMethod = transferMethod;
    }

    public String getPageAddress() {
        return pageAddress;
    }

    public void setPageAddress(String pageAddress) {
        this.pageAddress = pageAddress;
    }

    public TransferMethod getTransferMethod() {
        return transferMethod;
    }

    public void setTransferMethod(TransferMethod transferMethod) {
        this.transferMethod = transferMethod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PageGuide pageGuide = (PageGuide) o;

        if (pageAddress != null ? !pageAddress.equals(pageGuide.pageAddress) : pageGuide.pageAddress != null)
            return false;
        return transferMethod == pageGuide.transferMethod;
    }

    @Override
    public int hashCode() {
        int result = pageAddress != null ? pageAddress.hashCode() : 0;
        result = 31 * result + (transferMethod != null ? transferMethod.hashCode() : 0);
        return result;
    }

}