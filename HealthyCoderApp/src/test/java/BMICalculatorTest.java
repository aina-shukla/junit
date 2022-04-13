package test.java;

import static org.junit.Assume.assumeTrue;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import main.java.BMICalculator;
import main.java.Coder;

class BMICalculatorTest {

	private String env = "dev";

	@BeforeAll
	static void beforeAll() {
		System.out.println("Before ALL");
	}

	@AfterAll
	static void afterAll() {
		System.out.println("After ALL");
	}

	@BeforeEach
	void beforeEach() {
		System.out.println("Before each");
	}

	@AfterEach
	void afterEach() {
		System.out.println("After each");
	}

	@Test
	void returnTrueWhenDietRecommended() {
		// given
		double weight = 89.0, height = 1.72;

		// when
		boolean isRecommended = BMICalculator.isDietRecommended(weight, height);
 
		// then
		assertTrue(isRecommended);

		// assertTrue(BMICalculator.isDietRecommended(89.0, 1.72)); // assertTrue checks
		// true if method returns true
	}

	@ParameterizedTest
	@ValueSource(doubles = { 89.0, 95.0, 110.0 })
	void returnTrueWhenDietRecommended_oneParamTest(Double coderWeight) { // one parameter
		// given
		double weight = coderWeight, height = 1.72;

		// when
		boolean isRecommended = BMICalculator.isDietRecommended(weight, height);

		// then
		assertTrue(isRecommended);

		// assertTrue(BMICalculator.isDietRecommended(89.0, 1.72)); // assertTrue checks
		// true if method returns true
	}

	@ParameterizedTest
	@CsvSource(value = { "89.0, 1.72", "95.0, 1.75" })
	void returnTrueWhenDietRecommended_multipleParamTest(Double coderWeight, Double coderHeight) { // one parameter
		// given
		double weight = coderWeight, height = coderHeight;

		// when
		boolean isRecommended = BMICalculator.isDietRecommended(weight, height);

		// then
		assertTrue(isRecommended);

		// assertTrue(BMICalculator.isDietRecommended(89.0, 1.72)); // assertTrue checks
		// true if method returns true
	}

	@ParameterizedTest(name = "weight={0}, height={1}")
	@CsvSource(value = { "89.0, 1.72", "95.0, 1.75" })
	void returnTrueWhenDietRecommended_multipleParamTest2(Double coderWeight, Double coderHeight) { // one parameter
		assumeTrue(this.env.equals("prod"));

		// given
		double weight = coderWeight, height = coderHeight;

		// when
		boolean isRecommended = BMICalculator.isDietRecommended(weight, height);

		// then
		assertTrue(isRecommended);

		// assertTrue(BMICalculator.isDietRecommended(89.0, 1.72)); // assertTrue checks
		// true if method returns true
	}

	/*
	 * @ParameterizedTest(name = "weight={0}, height={1}")
	 * 
	 * @CsvFileSource(resources = "/diet-recommended-input-data.csv", numLinesToSkip
	 * = 1 ) void returnTrueWhenDietRecommended_multipleParamTest3(Double
	 * coderWeight, Double coderHeight) { // one parameter // given double weight =
	 * coderWeight, height = coderHeight;
	 * 
	 * // when boolean isRecommended = BMICalculator.isDietRecommended(weight,
	 * height);
	 * 
	 * // then assertTrue(isRecommended);
	 * 
	 * // assertTrue(BMICalculator.isDietRecommended(89.0, 1.72)); // assertTrue
	 * checks // true if method returns true }
	 */

	@Test
	void returnFalseWhenDietRecommended() {
		// given
		double weight = 50, height = 1.92;

		// when
		boolean isRecommended = BMICalculator.isDietRecommended(weight, height);

		// then
		assertFalse(isRecommended);

		// assertFalse(BMICalculator.isDietRecommended(50, 1.92)); // assertTrue checks
		// true if method returns true
	}

	@Test
	@DisplayName(">>>>>>>>>>>>>>>>method display name")
	void throwArithmeticExceptionWhenHeightIsZero() {
		// given
		double weight = 50, height = 0;

		// when
		Executable exec = () -> BMICalculator.isDietRecommended(weight, height);

		// then
		assertThrows(ArithmeticException.class, exec);
	}

	@Test
	@DisabledOnOs(OS.LINUX)
	void returnCoderWithWorstBMIWhenCoderListIsNotEmpty() {
		// given
		List<Coder> lists = new ArrayList<>();
		lists.add(new Coder(1.80, 60.0));
		lists.add(new Coder(1.82, 98.0));
		lists.add(new Coder(1.82, 64.7));

		// when
		Coder worstBMI = BMICalculator.findCoderWithWorstBMI(lists);

		// then
		assertAll(() -> assertEquals(1.82, worstBMI.getHeight()), () -> assertEquals(98.0, worstBMI.getWeight()));
	}

	@Nested
	@DisplayName(">>>>>>>>>>>>>>>>inner class display name")
	class WorstBMI {
		@Test
		@Disabled
		void returnNullCoderWithWorstBMIWhenCoderListIsEmpty() {
			// given
			List<Coder> lists = new ArrayList<>();

			// when
			Coder worstBMI = BMICalculator.findCoderWithWorstBMI(lists);

			// then
			assertNull(worstBMI);
		}

		@Test
		void returnBMIScoreArrayBMIWhenCoderListIsNotEmpty() {
			// given
			List<Coder> lists = new ArrayList<>();
			lists.add(new Coder(1.80, 60.0));
			lists.add(new Coder(1.82, 98.0));
			lists.add(new Coder(1.82, 64.7));
			double[] expected = { 18.52, 29.59, 19.53 };

			// when
			double[] bmiScores = BMICalculator.getBMIScores(lists);

			// then
			assertArrayEquals(expected, bmiScores);
		}
	}

}
