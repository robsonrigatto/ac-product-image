package br.com.rr.productimage.vo;

import java.io.Serializable;

public class ImageVO implements Serializable {

    private Long id;
    private String fileName;
    private String fileContent;

    public ImageVO() {}

    public ImageVO(Long id, String fileName, String fileContent) {
        this(fileName, fileContent);
        this.id = id;
    }

    public ImageVO(String fileName, String fileContent) {
        this.fileName = fileName;
        this.fileContent = fileContent;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileContent() {
        return fileContent;
    }

    public void setFileContent(String fileContent) {
        this.fileContent = fileContent;
    }
}
