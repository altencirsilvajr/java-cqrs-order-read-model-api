package dev.altencir.orders.architecture;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.*;
import com.tngtech.archunit.core.importer.ImportOption; import com.tngtech.archunit.junit.*;
@AnalyzeClasses(packages="dev.altencir.orders",importOptions=ImportOption.DoNotIncludeTests.class)
class ArchitectureTest {
 @ArchTest static final com.tngtech.archunit.lang.ArchRule domain_is_framework_free=noClasses().that().resideInAPackage("..domain..").should().dependOnClassesThat().resideInAnyPackage("org.springframework..","jakarta.persistence..");
 @ArchTest static final com.tngtech.archunit.lang.ArchRule api_does_not_reach_repositories=noClasses().that().resideInAPackage("..api..").should().dependOnClassesThat().haveSimpleNameEndingWith("Repository");
}
