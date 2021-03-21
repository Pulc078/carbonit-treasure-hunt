package fr.carbonit.treasurehunt.businessservice.base;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public abstract class BaseBSTest {

    /**
     * Contexte
     */
    @Autowired
    protected GenericApplicationContext context;

    /**
     * Initialisation du contexte dans les couches Simat
     */
    @BeforeEach
    public void beforeAll() {
        // Passage du contexte au différent resolver simat

        BaseBS.setContexte(context);
    }

}