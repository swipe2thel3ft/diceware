package edu.cnm.deepdive.diceware.model.dao;

import edu.cnm.deepdive.diceware.model.entity.Passphrase;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassphraseRepository extends JpaRepository<Passphrase, UUID> {

  Iterable<Passphrase> getAllByOrderByCreatedDesc();

}
