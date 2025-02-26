1. Jakarta ECS (ecs-1.4.2.jar)

    Files & Locations:
        SaveXmlFile.java
            Path: jalog/openlogbook/src/openlogbook/openlogbookimpl/filehandler/xmlfilehandler/SaveXmlFile.java
            Lines:
                Line 23: import org.apache.ecs.xml.XML;
                Line 24: import org.apache.ecs.xml.XMLDocument;

    How It's Used:
        This dependency is being used to create and manipulate XML structures using XML and XMLDocument classes.

    Suggested Replacement:
        Use JAXB (Java Architecture for XML Binding) or Jackson for XML for a modern approach to handling XML.
        Example:

        import javax.xml.bind.JAXBContext;
        import javax.xml.bind.JAXBException;
        import javax.xml.bind.Marshaller;

        Alternative: org.w3c.dom for manual XML creation.

2. SkinLF (skinlf.jar)

    Files & Locations:
        Genesis.java
            Path: jalog/openlogbook/Genesis.java
            Lines:
                Line 61: import com.l2fprod.gui.plaf.skin.Skin;
                Line 62: import com.l2fprod.gui.plaf.skin.SkinLookAndFeel;
        Genesis.java (Duplicate in another directory)
            Path: jalog/openlogbook/src/openlogbook/Genesis.java
            Lines:
                Line 64: import com.l2fprod.gui.plaf.skin.Skin;
                Line 65: import com.l2fprod.gui.plaf.skin.SkinLookAndFeel;
        OptionSkinChooser.java
            Path: jalog/openlogbook/src/openlogbook/openlogbookimpl/gui/optiondialog/OptionSkinChooser.java
            Lines:
                Line 3: import com.l2fprod.gui.plaf.skin.CompoundSkin;
                Line 4: import com.l2fprod.gui.plaf.skin.Skin;
                Line 5: import com.l2fprod.gui.plaf.skin.SkinLookAndFeel;
                Line 6: import com.l2fprod.gui.plaf.skin.SkinPreviewWindow;

    How It's Used:
        SkinLF is used to apply custom themes and skins to Swing UI components.

    Suggested Replacement:
        Use Substance Look-and-Feel for Swing applications:
            GitHub: https://github.com/kirill-grouchnikov/radiance
            Implementation Example:

            import org.pushingpixels.substance.api.skin.SubstanceGraphiteLookAndFeel;
            UIManager.setLookAndFeel(new SubstanceGraphiteLookAndFeel());

3. SizeOf (SizeOf.jar)

    Files & Locations:
        ObjectSizer.java
            Path: jalog/openlogbook/debug/instrumentation/ObjectSizer.java
            Lines:
                Line 6: import net.sourceforge.sizeof.SizeOf;
                Line 18: SizeOf.skipStaticField(true);
                Line 19: SizeOf.setMinSizeToLog(10);
                Line 20: SizeOf.turnOffDebug();
                Line 58: contents.addEntry(theClass.toString(), "" + SizeOf.deepSizeOf(theClass));
        ObjectSizer.java (Duplicate in another directory)
            Path: jalog/openlogbook/src/openlogbook/debug/instrumentation/ObjectSizer.java
            Lines (same as above)

    How It's Used:
        The SizeOf library is used to calculate memory sizes of Java objects.

    Suggested Replacement:
        Use Ehcache SizeOf instead:

import org.ehcache.sizeof.SizeOf;
long objectSize = SizeOf.newInstance().deepSizeOf(myObject);

    Maven Dependency for Ehcache SizeOf:

            <dependency>
                <groupId>org.ehcache</groupId>
                <artifactId>sizeof</artifactId>
                <version>2.1.2</version>
            </dependency>

Next Steps

    Replace the Imports:
        Update the affected Java files to remove references to the obsolete libraries and use the suggested replacements.

    Modify the build.gradle File:
        Remove:

implementation files("$libDir/ecs-1.4.2.jar")
implementation files("$libDir/skinlf.jar")
implementation files("$libDir/SizeOf.jar")

Add modern replacements:

    implementation 'org.ehcache:sizeof:2.1.2'
    implementation 'org.pushingpixels:radiance:4.0'
    implementation 'javax.xml.bind:jaxb-api:2.3.1'

Test & Validate:

    After making these changes, ensure all affected functionalities work correctly with the new dependencies.