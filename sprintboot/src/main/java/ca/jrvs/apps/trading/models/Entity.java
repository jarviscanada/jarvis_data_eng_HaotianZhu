package ca.jrvs.apps.trading.models;

public interface Entity<ID> {
    ID getId();
    void setId(ID id);
}
