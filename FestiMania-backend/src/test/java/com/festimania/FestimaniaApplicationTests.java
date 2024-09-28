package com.festimania;

import com.festimania.entities.Festival;
import com.festimania.entities.dto.FestivalCompleteDto;
import com.festimania.entities.dto.FestivalDto;
import com.festimania.exceptions.AttributeException;
import com.festimania.exceptions.ObjectNotFoundException;
import com.festimania.persistence.repositories.FestivalRepository;
import com.festimania.persistence.service.impl.FestivalServiceImpl;
import com.festimania.utils.ClassMapper;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Data
class FestivalServiceImplTest {

	private FestivalServiceImpl festivalService;

	private FestivalRepository festivalRepository;

	private ClassMapper classMapper;

	@BeforeEach
	void setUp() {
		festivalRepository = mock(FestivalRepository.class);
		classMapper = mock(ClassMapper.class);
		festivalService = new FestivalServiceImpl(festivalRepository, null, classMapper);
	}

	@Test
	void testAlterFestivalSuccessful() throws Exception {

		String festivalId = "1";
		FestivalDto updateFestival = new FestivalDto();
		updateFestival.setName("Festival Test");
		updateFestival.setDateStart("2024-09-25");
		updateFestival.setDateEnd("2024-09-28");
		updateFestival.setLocation("Location Test");
		updateFestival.setGenre("ROCK");
		updateFestival.setArtistsId(List.of("Artist1", "Artist2"));

		Festival festival = new Festival();
		festival.set_id(festivalId);
		festival.setName("Festival Test");

		when(festivalRepository.existsFestivalByNameIgnoreCase(festivalId)).thenReturn(false);
		when(festivalRepository.save(any(Festival.class))).thenReturn(festival);
		when(classMapper.toFestivalCompleteDto(any(Festival.class))).thenReturn(new FestivalCompleteDto());

		FestivalCompleteDto result = festivalService.alterFestival(festivalId, updateFestival);

		assertNotNull(result);
		verify(festivalRepository, times(1)).save(any(Festival.class));

	}

	@Test
	void testAlterFestivalThrowsObjectNotFoundException() {

		String festivalId = "1";
		FestivalDto updateFestival = new FestivalDto();
		updateFestival.setName("Festival Test");
		updateFestival.setDateStart("2024-09-25");
		updateFestival.setDateEnd("2024-09-28");

		when(festivalRepository.existsFestivalByNameIgnoreCase(festivalId)).thenReturn(true);

		assertThrows(ObjectNotFoundException.class, () -> {
			festivalService.alterFestival(festivalId, updateFestival);
		});

	}

	@Test
	void testAlterFestivalThrowsAttributeExceptionForBlankName() {

		String festivalId = "1";
		FestivalDto updateFestival = new FestivalDto();
		updateFestival.setName(""); // Blank name
		updateFestival.setDateStart("2024-09-25");
		updateFestival.setDateEnd("2024-09-28");

		when(festivalRepository.existsFestivalByNameIgnoreCase(festivalId)).thenReturn(false);

		assertThrows(AttributeException.class, () -> {
			festivalService.alterFestival(festivalId, updateFestival);
		});

	}

	@Test
	void testAlterFestivalThrowsAttributeExceptionForInvalidDates() {

		String festivalId = "1";
		FestivalDto updateFestival = new FestivalDto();
		updateFestival.setName("Festival Test");
		updateFestival.setDateStart("2024-09-30");
		updateFestival.setDateEnd("2024-09-28");

		when(festivalRepository.existsFestivalByNameIgnoreCase(festivalId)).thenReturn(false);

		assertThrows(AttributeException.class, () -> {
			festivalService.alterFestival(festivalId, updateFestival);
		});

	}
}