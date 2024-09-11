package com.example.frro.dao;

import com.example.frro.entity.VisitorEntry;
import com.example.frro.entity.VisitorRemarks;

public class VisitorWithRemarks {
    private VisitorEntry visitor;
    private VisitorRemarks remarks;

    public VisitorWithRemarks(VisitorEntry visitor, VisitorRemarks remarks) {
        this.visitor = visitor;
        this.remarks = remarks;
    }

    public VisitorEntry getVisitor() {
        return visitor;
    }

    public void setVisitor(VisitorEntry visitor) {
        this.visitor = visitor;
    }

    public VisitorRemarks getRemarks() {
        return remarks;
    }

    public void setRemarks(VisitorRemarks remarks) {
        this.remarks = remarks;
    }
}

