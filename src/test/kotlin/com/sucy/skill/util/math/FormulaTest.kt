package com.sucy.skill.util.math

import com.google.common.collect.ImmutableList
import com.sucy.skill.util.math.formula.Formula
import com.sucy.skill.util.math.formula.function.Sq
import com.sucy.skill.util.math.formula.operator.Divide
import com.sucy.skill.util.math.formula.operator.Plus
import com.sucy.skill.util.math.formula.operator.Times
import com.sucy.skill.util.math.formula.value.ConstValue
import com.sucy.skill.util.math.formula.value.VarValue
import io.kotlintest.shouldBe
import org.junit.Test

/**
 * SkillAPIKotlin © 2018
 */
class FormulaTest {

    @Test
    fun getTokensConstOnly() {
        val formula = Formula("3", ImmutableList.of())
        formula.tokens.size shouldBe 1
        formula.tokens[0] shouldBe ConstValue(3.0)
    }

    @Test
    fun getTokensVarOnly() {
        val formula = Formula("test", ImmutableList.of("test"))
        formula.tokens.size shouldBe 1
        formula.tokens[0] shouldBe VarValue(0)
    }

    @Test
    fun getTokensOperator() {
        val formula = Formula("2+3", ImmutableList.of())
        formula.tokens.size shouldBe 3
        formula.tokens[0] shouldBe ConstValue(2.0)
        formula.tokens[1] shouldBe ConstValue(3.0)
        formula.tokens[2] shouldBe Plus
    }

    @Test
    fun getTokensOperatorAndSpaces() {
        val formula = Formula("2 + 3", ImmutableList.of())
        formula.tokens.size shouldBe 3
        formula.tokens[0] shouldBe ConstValue(2.0)
        formula.tokens[1] shouldBe ConstValue(3.0)
        formula.tokens[2] shouldBe Plus
    }

    @Test
    fun getTokensFunction() {
        val formula = Formula("sq(2)", ImmutableList.of())
        formula.tokens.size shouldBe 2
        formula.tokens[0] shouldBe ConstValue(2.0)
        formula.tokens[1] shouldBe Sq
    }

    @Test
    fun getTokensMixedOps() {
        val formula = Formula("1+2*3+4", ImmutableList.of())
        formula.tokens.size shouldBe 7
        formula.tokens shouldBe ImmutableList.of(
                ConstValue(1.0),
                ConstValue(2.0),
                ConstValue(3.0),
                Times,
                Plus,
                ConstValue(4.0),
                Plus
        )
    }

    @Test
    fun getTokensComplex() {
        val formula = Formula("2 * (1 + sq(2 + 3) / (4 + 2))", ImmutableList.of())
        formula.tokens shouldBe ImmutableList.of(
                ConstValue(2.0),
                ConstValue(1.0),
                ConstValue(2.0),
                ConstValue(3.0),
                Plus,
                Sq,
                ConstValue(4.0),
                ConstValue(2.0),
                Plus,
                Divide,
                Plus,
                Times
        )
    }

    @Test
    fun getTokensParenthesisInFunc() {
        val formula = Formula("1 + sq((2 + 3) * 2) / 4", ImmutableList.of())
        formula.tokens shouldBe ImmutableList.of(
                ConstValue(1.0),
                ConstValue(2.0),
                ConstValue(3.0),
                Plus,
                ConstValue(2.0),
                Times,
                Sq,
                ConstValue(4.0),
                Divide,
                Plus
        )
    }

    @Test
    fun evaluate() {
        val formula = Formula("1 + sq((2 + 3) * 2) / 4", ImmutableList.of())
        formula.evaluate() shouldBe 26.0
    }

    @Test
    fun exponent() {
        val formula = Formula("25 * 2 ^ ( 0.2 + 1.8 )", ImmutableList.of())
        formula.evaluate() shouldBe 100.0
    }

    @Test
    fun evaluateVariables() {
        val formula = Formula("x * z + y", ImmutableList.of("x", "y", "z"))
        formula.evaluate(3.0, 4.0, 5.0) shouldBe 19.0
    }

    @Test
    fun evaluateParenthesisImpliedMultiplication() {
        val formula = Formula("2 ( 3 + 4 )", ImmutableList.of())
        formula.evaluate() shouldBe 14.0
    }

    @Test
    fun evaluateParenthesisImpliedMultiplicationWithMultipleParts() {
        val formula = Formula("2(3+4*5)", ImmutableList.of())
        formula.evaluate() shouldBe 46.0
    }
}