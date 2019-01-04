package net.vitic.ddd.readmodel.customer;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

/**
 * This is an "Atomic & Triggered Fitness Function" defined at the
 * Technical Architectural Dimension (from the book "Evolutionary Architecture").
 *
 * It uses the <a href="https://www.archunit.org/userguide/html/000_Index.html">
 *     com.tngtech.archunit:archunit</a> test library.
 */
class ArchFitnessFunction {

    @Test
    void application_service_should_only_be_accessed_by_ports_and_adapters() {
        JavaClasses classes = new ClassFileImporter().importPackages("net.vitic.ddd.readmodel.customer");

        ArchRule myRule = classes()
            .that().resideInAPackage("..application..")
            .should().onlyBeAccessed().byAnyPackage("..application..",
                                                    "..port.adapter..");

        myRule.check(classes);
    }
}
