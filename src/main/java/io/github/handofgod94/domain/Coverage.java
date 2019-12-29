package io.github.handofgod94.domain;

import java.util.Objects;

public class Coverage {

  /**
   * Enum constants for coverage category.
   * Jacoco CSV Report generates results of various categories
   * such as branch missed/covered, instructions missed/covered.
   * This enum maps each of those categories.
   *
   * <p>It includes:
   * <code>INSTRUCTION, BRANCH, LINE, COMPLEXITY, METHOD</code>
   */
  public enum CoverageCategory {
    INSTRUCTION,
    BRANCH,
    LINE,
    COMPLEXITY,
    METHOD
  }

  public static final float INVALID_COVERAGE_PERCENTAGE = 0f;

  public long covered;
  public long missed;
  public CoverageCategory category;
  public Report report;

  Coverage(long covered, long missed, CoverageCategory category) {
    this.covered = covered;
    this.missed = missed;
    this.category = category;
  }

  Coverage(CoverageCategory category, Report report) {
    this.category = category;
    this.report = report;
  }

  public static Coverage create(long covered, long missed, CoverageCategory category) {
    return new Coverage(covered, missed, category);
  }

  public static Coverage create(CoverageCategory category, Report report) {
    return new Coverage(category, report);
  }

  /**
   * Calculates coverage percentage.
   * @return float value if coverage calculation is success,
   *     INVALID_COVERAGE_PERCENTAGE = 0f otherwise
   */
  public float getCoveragePercentage() {
    if (covered < 0
        || missed < 0) {
      return INVALID_COVERAGE_PERCENTAGE;
    }

    float totalInstructions = covered + missed;
    float result = (covered / totalInstructions) * 100.0f;
    return result;
  }

  public long getCovered() {
    return covered;
  }

  public long getMissed() {
    return missed;
  }

  @Override
  public String toString() {
    return "Coverage{" +
      "covered=" + covered +
      ", missed=" + missed +
      ", category=" + category +
      '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Coverage coverage = (Coverage) o;
    return covered == coverage.covered &&
      missed == coverage.missed &&
      category == coverage.category;
  }

  @Override
  public int hashCode() {
    return Objects.hash(covered, missed, category);
  }

  /**
   * Generates an instance of Coverage by calculating the coverage for given category.
   */
  public void loadCoverage() {
    switch (category) {
      case INSTRUCTION:
        InstructionCoverage coverage = new InstructionCoverage(category, report);
        covered = coverage.calculateCovered();
        missed = coverage.calculateMissed();
        break;
      case LINE:
        LineCoverage coverage1 = new LineCoverage(category, report);
        covered = coverage1.calculateCovered();
        missed = coverage1.calculateMissed();
        break;
      case BRANCH:
        BranchCoverage coverage2 = new BranchCoverage(category, report);
        covered = coverage2.calculateCovered();
        missed = coverage2.calculateMissed();
        break;
      case METHOD:
        MethodCoverage methodCoverage = new MethodCoverage(category, report);
        covered = methodCoverage.calculateCovered();
        missed = methodCoverage.calculateMissed();
        break;
      case COMPLEXITY:
        covered = totalCoveredComplexity();
        missed = totalMissedComplexity();
        break;
      default:
        throw new IllegalArgumentException("Invalid Coverage Category provided");
    }
  }


  // missed instructions
  private final long totalMissedInstruction() {
    throw new IllegalAccessError("It should not come here");
  }

  private final long totalMissedLine() {
    throw new IllegalAccessError("It should not come here");
  }

  private final long totalMissedBranch() {
    throw new IllegalAccessError("It should not come here");
  }

  private final long totalMissedMethod() {
    throw new IllegalAccessError("It should not come here");
  }

  private final long totalMissedComplexity() {
    return report.getLines().map(ReportLine::getComplexityMissed).sum().longValue();
  }


  // covered instructions
  private final long totalCoveredInstruction() {
    throw new IllegalAccessError("It should not come here");
  }

  private final long totalCoveredLine() {
    throw new IllegalAccessError("It should not come here");
  }

  private final long totalCoveredBranch() {
    throw new IllegalAccessError("It should not come here");
  }

  private final long totalCoveredMethod() {
    throw new IllegalAccessError("It should not come here");
  }

  private final long totalCoveredComplexity() {
    return report.getLines().map(ReportLine::getComplexityCovered).sum().longValue();
  }
}
