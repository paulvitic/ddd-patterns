package net.vitic.ddd.readmodel.customer;

import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.library.Architectures.layeredArchitecture;

/**
 * This is an "Atomic & Triggered Fitness Function" defined at the
 * Technical Architectural Dimension (from the book "Evolutionary Architecture").
 *
 * It uses the <a href="https://www.archunit.org/userguide/html/000_Index.html">
 *     com.tngtech.archunit:archunit</a> test library.
 */
class ArchFitnessFunction {

    private final String rootModule = "net.vitic.ddd.readmodel.customer";
    private final String applicationServiceModule = "..application..";
    private final String portsAndAdaptersModule = "..port.adapter..";
    private final String domainModule = "..domain..";

    @Test
    void application_service_should_only_be_accessed_by_ports_and_adapters() {

        classes()
            .that().resideInAPackage(applicationServiceModule)
            .should().onlyBeAccessed().byAnyPackage(applicationServiceModule, portsAndAdaptersModule)
            .check(new ClassFileImporter().importPackages(rootModule));
    }

    @Test
    void modules_should_be_layered_according_to_hexagonal_architecture() {
        final String domainCore = "Domain";
        final String applicationService = "Application Service";
        final String portsAndAdapters = "Ports & Adapters";

        layeredArchitecture()
            .layer(domainCore).definedBy(domainModule)
            .layer(applicationService).definedBy(applicationServiceModule)
            .layer(portsAndAdapters).definedBy(portsAndAdaptersModule)

            .whereLayer(domainCore).mayOnlyBeAccessedByLayers(applicationService, portsAndAdapters)
            .whereLayer(applicationService).mayOnlyBeAccessedByLayers(portsAndAdapters)
            .whereLayer(portsAndAdapters).mayNotBeAccessedByAnyLayer()

            .check(new ClassFileImporter().importPackages(rootModule));
    }
}
