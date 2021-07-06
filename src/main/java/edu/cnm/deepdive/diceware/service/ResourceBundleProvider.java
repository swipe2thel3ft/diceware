package edu.cnm.deepdive.diceware.service;

import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceBundleProvider implements WordListProvider {

  private final List<String> words;

  @Autowired
  public ResourceBundleProvider(ResourceBundle bundle) {
    words = new LinkedList<>();
    for (String key : bundle.keySet()) {
      words.add(bundle.getString(key));
    }
  }

  @Override
  public List<String> getWords() {
    return words;
  }

}