package de.bonn.eis.models;

import java.util.List;

/**
 * Created by korovin on 3/24/2017.
 */
public class AnnotationListModel {
    private List<AnnotationRequestModel> list;

    public AnnotationListModel(List<AnnotationRequestModel> list) {
        this.list = list;
    }

    public AnnotationListModel() {
    }

    public List<AnnotationRequestModel> getList() {
        return list;
    }

    public void setList(List<AnnotationRequestModel> list) {
        this.list = list;
    }
}
