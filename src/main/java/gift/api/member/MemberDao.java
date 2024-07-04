package gift.api.member;

import java.sql.Types;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

@Repository
public class MemberDao {

    private final JdbcClient jdbcClient;

    public MemberDao(JdbcClient jdbcClient) {
        this.jdbcClient = jdbcClient;
    }

    public boolean hasMemberByEmail(String email) {
        return jdbcClient.sql("select * from member where email = :email")
                        .param("email", email)
                        .query(Member.class)
                        .optional()
                        .isPresent();
    }

    public void insert(MemberRequestDto memberRequestDto) {
        jdbcClient.sql("insert into member (email, password, role) values (:email, :password, :role)")
            .param("email", memberRequestDto.getEmail(), Types.VARCHAR)
            .param("password", memberRequestDto.getPassword(), Types.VARCHAR)
            .param("role", memberRequestDto.getRole(), Types.VARCHAR)
            .update();
    }
}