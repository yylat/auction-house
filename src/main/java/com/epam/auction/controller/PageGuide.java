package com.epam.auction.controller;

/**
 * Represents page and method to go to this page.
 */
public class PageGuide {

    /**
     * Address of the page.
     */
    private String pageAddress;
    /**
     * Method of transfer to page.
     */
    private TransferMethod transferMethod;

    /**
     * Constructs PageGuide without parameters.
     */
    public PageGuide() {
    }

    /**
     * Constructs PageGuide with page address.
     *
     * @param pageAddress page address
     */
    public PageGuide(String pageAddress) {
        this.pageAddress = pageAddress;
    }

    /**
     * Constructs PageGuide with page address and transfer method.
     *
     * @param pageAddress    page address
     * @param transferMethod transfer method
     */
    public PageGuide(String pageAddress, TransferMethod transferMethod) {
        this.pageAddress = pageAddress;
        this.transferMethod = transferMethod;
    }

    /**
     * Returns address of the page.
     *
     * @return address of the page
     */
    String getPageAddress() {
        return pageAddress;
    }

    /**
     * Sets address of the page.
     *
     * @param pageAddress address of the page
     */
    public void setPageAddress(String pageAddress) {
        this.pageAddress = pageAddress;
    }

    /**
     * Returns transfer method.
     *
     * @return transfer method
     */
    TransferMethod getTransferMethod() {
        return transferMethod;
    }

    /**
     * Sets transfer method.
     *
     * @param transferMethod transfer method
     */
    public void setTransferMethod(TransferMethod transferMethod) {
        this.transferMethod = transferMethod;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PageGuide pageGuide = (PageGuide) o;

        return (pageAddress != null ? pageAddress.equals(pageGuide.pageAddress) : pageGuide.pageAddress == null)
                && transferMethod == pageGuide.transferMethod;
    }

    @Override
    public int hashCode() {
        int result = pageAddress != null ? pageAddress.hashCode() : 0;
        result = 31 * result + (transferMethod != null ? transferMethod.hashCode() : 0);
        return result;
    }

}