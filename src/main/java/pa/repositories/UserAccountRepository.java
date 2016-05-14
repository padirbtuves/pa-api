package pa.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import pa.domain.UserAccount;

@Repository
public interface UserAccountRepository extends CrudRepository<UserAccount, Long> {

	UserAccount findOneByGoogleId(String googleId);
	
	UserAccount findOneByTagId(String tagId);
}
