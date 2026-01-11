package com.sara.pocketbanker.repository;

import com.sara.pocketbanker.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AccountRepository extends JpaRepository<Account,String> {
}
