package ro.itschool.controller.mapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ro.itschool.controller.model.UserDTO;
import ro.itschool.entity.User;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    public static final Long ID = 1L;
    public static final String NAME = "Rocco";

    UserMapper userMapper;

    @BeforeEach
    void setUp() {
        userMapper = new UserMapper();
    }

    @Test
    void testNullObject() {
        assertNull(UserMapper.convertToDTO(null));
    }

    @Test
    void testEmptyObject() {
        assertNotNull(UserMapper.convertToDTO(new User()));
    }
    @Test
    void convertToDTO() {
        User user = new User();
        user.setId(ID);
        user.setUsername(NAME);

        UserDTO userDTO = UserMapper.convertToDTO(user);
        assertEquals(ID, userDTO.getId());
        assertEquals(NAME, userDTO.getUsername());
    }
}