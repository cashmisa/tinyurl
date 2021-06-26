package dev.xiaowen.demo.repo;

import org.springframework.stereotype.Repository;
import dev.xiaowen.demo.data.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

@Repository
public interface UrlConverterRepo extends JpaRepository<Url, String> {
	@Query(value = "SELECT nextval(hibernate_seq)", nativeQuery = true)
	public long getNextCode();
	public Url findByShortUrl(String shortUrl);
}
