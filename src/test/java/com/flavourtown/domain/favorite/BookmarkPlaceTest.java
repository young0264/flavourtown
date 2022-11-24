package com.flavourtown.domain.bookmark;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.transaction.Transactional;

@SpringBootTest
@Transactional
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "/application-test.properties")
class BookmarkPlaceTest {


}