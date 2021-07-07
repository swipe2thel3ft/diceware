package edu.cnm.deepdive.diceware.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Word {

  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(name = "word_id", columnDefinition = "CHAR(16) FOR BIT DATA",
      nullable = false, updatable = false)
  @Id
  @JsonIgnore
  private UUID id;

  @Column(name = "word_text", nullable = false, updatable = false)
  private String text;

  @Column(name = "word_order", nullable = false, updatable = false)
  private int order;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "passphrase_id", nullable = false, updatable = false)
  @JsonIgnore
  private Passphrase passphrase;

  public UUID getId() {
    return id;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public int getOrder() {
    return order;
  }

  public void setOrder(int order) {
    this.order = order;
  }

  public Passphrase getPassphrase() {
    return passphrase;
  }

  public void setPassphrase(Passphrase passphrase) {
    this.passphrase = passphrase;
  }

}
