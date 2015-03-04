package com.epam.edu.kh.tests;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.acls.model.NotFoundException;

import com.epam.edu.kh.business.dao.RecordDao;
import com.epam.edu.kh.business.dao.RecordDaoImpl;
import com.epam.edu.kh.business.entity.Record;
import com.epam.edu.kh.business.scanner.JsonScanner;
import com.epam.edu.kh.business.service.RecordService;
import com.epam.edu.kh.business.service.RecordServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;

import static org.junit.Assert.*;

public class Tests {

	/*
	 * JsonScanner js; RecordService recordService; RecordDao recordDao;
	 * 
	 * @Before public void create() {
	 * 
	 * js = Mockito.mock(JsonScanner.class); recordService =
	 * Mockito.mock(RecordService.class); recordDao =
	 * Mockito.mock(RecordDao.class); }
	 */
	JsonScanner json = new JsonScanner();

	@Test
	public void test1() throws NullPointerException, IOException {

		Record rec1 = new Record();
		rec1.setUserName("Лепра");
		Record rec2 = json
				.parseJsonFromVkServer("http://vk.com/id265302295?w=wall265302295_72");
		assertEquals(rec1.getUserName(), rec2.getUserName());
	}

	@Test
	public void test2() throws NullPointerException, IOException {

		Record rec1 = json
				.parseJsonFromVkServer("http://vk.com/wall-24502885_168300");
		Record rec2 = json
				.parseJsonFromVkServer("http://vk.com/wall-24502885_168295");
		assertEquals(rec1.getUserName(), rec2.getUserName());
	}

	@Test
	public void test3() throws NullPointerException, IOException {

		assertEquals("-24502885_168300",
				json.parseLinkVk("http://vk.com/wall-24502885_168300"));
	}

	@Test(expected = NotFoundException.class)
	public void test4() throws NullPointerException, IOException {

		assertFalse("-24502885_168300".equals(json
				.parseLinkVk("http://vk.com/wallwqerqweqwe_168300")));
	}

	@Test(expected = NotFoundException.class)
	public void test5() throws NullPointerException, IOException {

		assertEquals("-24502885_168300",
				json.parseLinkVk("http://vk.com/wa-24502885_168300"));
	}

	@Test(expected = NotFoundException.class)
	public void test6() throws NullPointerException, IOException {

		Record rec1 = new Record();
		Record rec2 = new Record();

		rec1.setSourceUrl("http://vk.com/wa-24502885_168300");
		rec2.setSourceUrl("http://vk.com/wa-24502885_168300");
		assertTrue(rec1.equals(rec2));
	}

	@Test
	public void test7() throws NullPointerException, IOException {

		Record rec1 = new Record();
		rec1.setMessage("Однажды ты спросишь меня, что для меня на первом месте: ты или программирование?"
				+"Я отвечу тебе, что программирование.И ты уйдёшь, так и не узнав, что ты для меня на "
				+ "нулевом месте.");
		Record rec2 = json
				.parseJsonFromVkServer("http://vk.com/id265302295?w=wall265302295_72");
		assertTrue(rec1.getMessage().equals(rec2.getMessage()));
	}
	/*
	 * @Test(expected = NotFoundException.class) public void testLinkParser1()
	 * throws JsonProcessingException, IOException, Exception {
	 * 
	 * Mockito.when(js.parseLinkVk("http://vk.com/id265302295?w=asdasdasd_72"))
	 * .thenThrow(new NotFoundException("bad link"));
	 * 
	 * assertEquals( js.parseLinkVk("http://vk.com/id265302295?w=asdasdasd_72"),
	 * "-30022666_127724"); }
	 */

	/*
	 * @Test // (expeScted = NullPointerException.class) public void
	 * testParserJson() throws NullPointerException, IOException {
	 * 
	 * Mockito.when(
	 * js.parseJsonFromVkServer("http://vk.com/id265302295?w=wall265302295_72"))
	 * .thenThrow( new NullPointerException(
	 * "resp or wall or groups are null"));
	 * 
	 * assertNotNull(js
	 * .parseJsonFromVkServer("http://vk.com/id265302295?w=wall265302295_72"));
	 * 
	 * }
	 */
}
