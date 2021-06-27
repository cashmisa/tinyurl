package dev.xiaowen.demo.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import dev.xiaowen.demo.data.Url;

@Repository
public interface UrlConverterRepo extends JpaRepository<Url, String> {
	@Query(value = "SELECT nextval(hibernate_seq)", nativeQuery = true)
	public long getNextCode();

	public Optional<Url> findById(long id);
}
