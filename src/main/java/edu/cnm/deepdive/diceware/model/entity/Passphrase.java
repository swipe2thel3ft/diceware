package edu.cnm.deepdive.diceware.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Passphrase {

  @GeneratedValue(generator = "uuid2")
  @GenericGenerator(name = "uuid2", strategy = "uuid2")
  @Column(name = "passphrase_id", columnDefinition = "CHAR(16) FOR BIT DATA",
      nullable = false, updatable = false)
  @Id
  private UUID id;

  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false, updatable = false)
  private Date created;

  @Transient
  private int length;

  @OneToMany(fetch = FetchType.EAGER, mappedBy = "passphrase",
      cascade = CascadeType.ALL, orphanRemoval = true)
  @OrderBy("order ASC")
  private final List<Word> words = new LinkedList<>();

  public UUID getId() {
    return id;
  }

  public Date getCreated() {
    return created;
  }

  @JsonIgnore
  public int getLength() {
    return length;
  }

  public void setLength(int length) {
    this.length = length;
  }

  public List<Word> getWords() {
    return words;
  }

  @JsonProperty(value = "length")
  public int getWordCount() {
    return words.size();
  }

}
