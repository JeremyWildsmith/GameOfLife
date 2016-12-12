package ca.bcit.comp2526.a2b;

import ca.bcit.comp2526.a2b.OrganismConfiguration.BaseSeekBehavior;
import ca.bcit.comp2526.a2b.simulation.behavior.AgeDependantGraphic;
import ca.bcit.comp2526.a2b.simulation.behavior.GraphicBehavior;
import ca.bcit.comp2526.a2b.simulation.behavior.GraphicBehavior.NullGraphicBehavior;
import ca.bcit.comp2526.a2b.simulation.behavior.StaticGraphicBehavior;
import ca.bcit.comp2526.a2b.graphic.AnimatedGraphic;
import ca.bcit.comp2526.a2b.graphic.AnimatedGraphicFactory;
import ca.bcit.comp2526.a2b.graphic.ColorGraphic;
import ca.bcit.comp2526.a2b.res.CachedImageFactory;
import ca.bcit.comp2526.a2b.simulation.Flesh;
import ca.bcit.comp2526.a2b.simulation.TraverseStratedgy;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBox;

/**
 * Simulation configuration window.
 *
 * @author Jeremy Wildsmith
 * @version 1.0
 */
public class SimulationConfiguration extends javax.swing.JDialog {

    private final String PLANT_IMAGE = "plant.png";
    private final int IMAGE_WIDTH = 32;

    private final Map<Flesh, JCheckBox> consumables = new HashMap<>();
    private final Map<TraverseStratedgy, JCheckBox> accessibles = new HashMap<>();

    private GraphicBehavior graphicBehavior = new NullGraphicBehavior();

    private final DefaultListModel<OrganismConfiguration> creaturesSetModel = new DefaultListModel<>();
    private final DefaultComboBoxModel<BaseSeekBehavior> baseSeekComboBoxModel = new DefaultComboBoxModel<>();

    /**
     * Creates new form SimulationConfiguration
     */
    public SimulationConfiguration() {
        initComponents();

        creaturesSet.setModel(creaturesSetModel);
        baseSeekComboBox.setModel(baseSeekComboBoxModel);

        creaturesSetModel.addElement(createPlantConfiguration());
        creaturesSetModel.addElement(createHerbivoreConfiguration());
        creaturesSetModel.addElement(createOmnivoreConfiguration());
        creaturesSetModel.addElement(createCarnivoreConfiguration());

        graphicBehaviorName.setText(graphicBehavior.toString());
        consumablesPanel.setLayout(new GridLayout(Flesh.values().length, 1));
        accessiblePanel.setLayout(new GridLayout(TraverseStratedgy.values().length, 1));

        for (Flesh f : Flesh.values()) {
            JCheckBox chkBox = new JCheckBox(f.name());
            consumablesPanel.add(chkBox);
            consumables.put(f, chkBox);
        }

        for (TraverseStratedgy s : TraverseStratedgy.values()) {
            JCheckBox chkBox = new JCheckBox(s.name());
            accessiblePanel.add(chkBox);
            accessibles.put(s, chkBox);
        }

        for (BaseSeekBehavior b : BaseSeekBehavior.values()) {
            baseSeekComboBoxModel.addElement(b);
        }
    }

    /*
    Creates a plant configuraiton.
     */
    private OrganismConfiguration createPlantConfiguration() {
        OrganismConfiguration configuration = new OrganismConfiguration();

        Image srcImage = new CachedImageFactory().create(PLANT_IMAGE);
        AnimatedGraphic graphic = new AnimatedGraphicFactory().createSliceAnimation(srcImage, IMAGE_WIDTH, 10);

        AgeDependantGraphic.GraphicTransition transitions[] = new AgeDependantGraphic.GraphicTransition[]{
            new AgeDependantGraphic.GraphicTransition(4, graphic.getFrame(2)),
            new AgeDependantGraphic.GraphicTransition(7, graphic.getFrame(1)),
            new AgeDependantGraphic.GraphicTransition(10, graphic.getFrame(0)),};

        configuration.setSpeciesName("Plant");
        configuration.setVisibility(0);
        configuration.setMaxAge(10);
        configuration.setBaseSeekBehavior(BaseSeekBehavior.None);

        configuration.setAccessibilityRadius(1);
        configuration.setStratedgies(new TraverseStratedgy[]{TraverseStratedgy.Walk});
        configuration.setParentsRequired(3);
        configuration.setSpaceRequired(2);
        configuration.setFoodRequired(0);

        configuration.setMinChildren(1);
        configuration.setMaxChildren(2);

        configuration.setMinReproduceAge(1);
        configuration.setMaxReproduceAge(Integer.MAX_VALUE);

        configuration.setFoodLife(10);

        configuration.setGraphicBehavior(new AgeDependantGraphic(transitions));
        configuration.setFlesh(Flesh.Plant);

        configuration.setSpawnInPacks(true);
        configuration.setSpawnProbability(.3F);

        return configuration;
    }

    /*
    Creates a herbivore configuraiton.
     */
    private OrganismConfiguration createHerbivoreConfiguration() {
        OrganismConfiguration configuration = new OrganismConfiguration();

        configuration.setSpeciesName("Herbivore");
        configuration.setBaseSeekBehavior(BaseSeekBehavior.SeekFlesh);
        configuration.setVisibility(3);
        configuration.setMaxAge(Integer.MAX_VALUE);
        configuration.setAccessibilityRadius(1);
        configuration.setStratedgies(new TraverseStratedgy[]{TraverseStratedgy.Walk, TraverseStratedgy.Swim});
        configuration.setParentsRequired(2);
        configuration.setSpaceRequired(2);
        configuration.setFoodRequired(2);

        configuration.setMinChildren(1);
        configuration.setMaxChildren(2);

        configuration.setMinReproduceAge(1);
        configuration.setMaxReproduceAge(Integer.MAX_VALUE);

        configuration.setFoodLife(6);
        configuration.setGraphicBehavior(new StaticGraphicBehavior(new ColorGraphic(Color.yellow)));

        configuration.setFlesh(Flesh.Meat);
        configuration.setSpawnProbability(.25F);
        configuration.setConsumes(new Flesh[]{Flesh.Plant});

        return configuration;
    }

    /*
    Creates an omnivore configuration.
     */
    private OrganismConfiguration createOmnivoreConfiguration() {
        OrganismConfiguration configuration = new OrganismConfiguration();

        configuration.setSpeciesName("Omnivore");
        configuration.setBaseSeekBehavior(BaseSeekBehavior.SeekFlesh);
        configuration.setVisibility(3);
        configuration.setMaxAge(Integer.MAX_VALUE);
        configuration.setAccessibilityRadius(1);
        configuration.setStratedgies(new TraverseStratedgy[]{TraverseStratedgy.Walk, TraverseStratedgy.Swim});
        configuration.setParentsRequired(2);
        configuration.setSpaceRequired(3);
        configuration.setFoodRequired(3);

        configuration.setMinChildren(1);
        configuration.setMaxChildren(2);

        configuration.setMinReproduceAge(1);
        configuration.setMaxReproduceAge(Integer.MAX_VALUE);

        configuration.setFoodLife(4);
        configuration.setGraphicBehavior(new StaticGraphicBehavior(new ColorGraphic(new Color(255, 0, 179))));

        configuration.setFlesh(Flesh.Meat);
        configuration.setSpawnProbability(.1F);

        configuration.setConsumes(new Flesh[]{Flesh.Meat, Flesh.Plant});

        return configuration;
    }

    /*
    Creates a carnivore configuration.
     */
    private OrganismConfiguration createCarnivoreConfiguration() {
        OrganismConfiguration configuration = new OrganismConfiguration();

        configuration.setSpeciesName("Carnivore");
        configuration.setBaseSeekBehavior(BaseSeekBehavior.SeekFlesh);
        configuration.setVisibility(3);
        configuration.setMaxAge(Integer.MAX_VALUE);
        configuration.setAccessibilityRadius(2);
        configuration.setStratedgies(new TraverseStratedgy[]{TraverseStratedgy.Walk});
        configuration.setParentsRequired(2);
        configuration.setSpaceRequired(3);
        configuration.setFoodRequired(3);

        configuration.setMinChildren(1);
        configuration.setMaxChildren(1);

        configuration.setMinReproduceAge(1);
        configuration.setMaxReproduceAge(Integer.MAX_VALUE);

        configuration.setFoodLife(3);
        configuration.setGraphicBehavior(new StaticGraphicBehavior(new ColorGraphic(Color.red)));

        configuration.setFlesh(Flesh.Meat);
        configuration.setSpawnProbability(.1F);

        configuration.setConsumes(new Flesh[]{Flesh.Meat});

        return configuration;
    }

    /**
     * Gets all organisms defined in this configuration.
     *
     * @return All defined organisms in the configuraiton.
     */
    public OrganismConfiguration[] getOrganisms() {
        ArrayList<OrganismConfiguration> organisms = new ArrayList<>();

        for (int i = 0; i < creaturesSetModel.getSize(); i++) {
            organisms.add(creaturesSetModel.getElementAt(i));
        }

        return organisms.toArray(new OrganismConfiguration[organisms.size()]);
    }

    /**
     * Gets the simulation world width.
     *
     * @return The world width.
     */
    public int getWorldWidth() {
        return worldSize.getValue();
    }

    /**
     * Gets the simulation world height.
     *
     * @return world height
     */
    public int getWorldHeight() {
        return worldSize.getValue();
    }

    /**
     * Gets the water probability.
     *
     * @return The waster probability
     */
    public float getWaterProbability() {
        return waterProbability.getValue() / 100.0F;
    }

    /**
     * Gets the number of steps to simulate.
     *
     * @return The number of steps to simulate.
     */
    public int getNumberSimulates() {
        return (Integer) numberSimulates.getValue();
    }

    /**
     * Wheter the configuration is auto played or not.
     *
     * @return To autoplay or not.
     */
    public boolean isAutoPlay() {
        return autoPlaySimulation.isSelected();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        worldSize = new javax.swing.JSlider();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        waterProbability = new javax.swing.JSlider();
        numberSimulates = new javax.swing.JSpinner();
        autoPlaySimulation = new javax.swing.JCheckBox();
        jScrollPane3 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        creaturesSet = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        speciesName = new javax.swing.JTextField();
        spawnProbability = new javax.swing.JSlider();
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        fleshType = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        consumablesPanel = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        isCannibal = new javax.swing.JCheckBox();
        spawnInPacks = new javax.swing.JCheckBox();
        jLabel9 = new javax.swing.JLabel();
        baseSeekComboBox = new javax.swing.JComboBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jLabel10 = new javax.swing.JLabel();
        visibility = new javax.swing.JSpinner();
        jLabel11 = new javax.swing.JLabel();
        foodLife = new javax.swing.JSpinner();
        jLabel12 = new javax.swing.JLabel();
        maxAge = new javax.swing.JSpinner();
        jLabel13 = new javax.swing.JLabel();
        minReproduceAge = new javax.swing.JSpinner();
        jLabel14 = new javax.swing.JLabel();
        maxReproduceAge = new javax.swing.JSpinner();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        parentsRequired = new javax.swing.JSpinner();
        foodRequired = new javax.swing.JSpinner();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        spaceRequired = new javax.swing.JSpinner();
        jLabel19 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        accessiblePanel = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        graphicBehaviorName = new javax.swing.JTextField();
        browseGraphicBehavior = new javax.swing.JButton();
        saveOrganismConfiguration = new javax.swing.JButton();
        jLabel22 = new javax.swing.JLabel();
        maxChildren = new javax.swing.JSpinner();
        jLabel23 = new javax.swing.JLabel();
        minChildren = new javax.swing.JSpinner();
        jLabel24 = new javax.swing.JLabel();
        accessibility = new javax.swing.JSpinner();
        simulate = new javax.swing.JButton();

        setName("Configure Simulation"); // NOI18N

        jLabel4.setText("World Size");

        worldSize.setMaximum(150);
        worldSize.setMinimum(15);
        worldSize.setValue(40);

        jLabel25.setText("Steps To Simulate");

        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setText("Water Probability");

        waterProbability.setValue(3);

        numberSimulates.setValue(300);

        autoPlaySimulation.setSelected(true);
        autoPlaySimulation.setText("Autoplay Simulation");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(118, 118, 118)
                        .addComponent(jLabel4)
                        .addGap(0, 108, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(autoPlaySimulation)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(worldSize, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
                            .addComponent(jLabel26, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel25)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(numberSimulates))
                            .addComponent(waterProbability, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(worldSize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(jLabel26)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(waterProbability, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(57, 57, 57)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(numberSimulates, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(43, 43, 43)
                .addComponent(autoPlaySimulation)
                .addContainerGap(55, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("World Parameters", jPanel1);

        jScrollPane3.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        jPanel2.setMaximumSize(new java.awt.Dimension(311, 32767));

        creaturesSet.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                creaturesSetValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(creaturesSet);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Creatures");

        jLabel2.setText("Species Name");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Spawn Rate");

        jLabel6.setText("Flesh Type");

        fleshType.setModel(new DefaultComboBoxModel(Flesh.values()));

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Consumes");

        consumablesPanel.setLayout(new java.awt.GridLayout(100, 0));
        jScrollPane2.setViewportView(consumablesPanel);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Seek Behavior");

        isCannibal.setText("Is Cannibal");

        spawnInPacks.setText("Spawn In Packs");

        jLabel9.setText("Base Behavior");

        baseSeekComboBox.setActionCommand("baseBehaviorComboBox");

        jCheckBox3.setText("Flee Behavior");

        jLabel10.setText("Visibility");

        jLabel11.setText("Food Life");

        jLabel12.setText("Max Age");

        jLabel13.setText("Min Reproduce Age");

        jLabel14.setText("Max Reproduce Age");

        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Reproduction");

        jLabel16.setText("Parents Required");

        jLabel17.setText("Food Required");

        jLabel18.setText("Space Required");

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("Traverse Abilities");

        accessiblePanel.setLayout(new java.awt.GridLayout(1, 0));
        jScrollPane4.setViewportView(accessiblePanel);

        jLabel20.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setText("Graphical Behavior");

        jLabel21.setText("Name");

        graphicBehaviorName.setEditable(false);
        graphicBehaviorName.setText("jTextField2");

        browseGraphicBehavior.setText("...");
        browseGraphicBehavior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                browseGraphicBehaviorActionPerformed(evt);
            }
        });

        saveOrganismConfiguration.setText("Save");
        saveOrganismConfiguration.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveOrganismConfigurationActionPerformed(evt);
            }
        });

        jLabel22.setText("Max Children");

        jLabel23.setText("Min Children");

        jLabel24.setText("Accessibility");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(saveOrganismConfiguration, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane4)
                    .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(foodLife, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(maxAge, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                            .addComponent(fleshType, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(spawnProbability, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jCheckBox3)
                            .addComponent(baseSeekComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(graphicBehaviorName, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(browseGraphicBehavior, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel24)
                        .addGap(18, 18, 18)
                        .addComponent(accessibility))
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(spawnInPacks)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(visibility)
                            .addComponent(speciesName, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)))
                    .addComponent(jLabel11)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel16)
                                    .addComponent(jLabel17)
                                    .addComponent(jLabel18))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(foodRequired, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
                                    .addComponent(spaceRequired, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(parentsRequired)))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel23)
                                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(maxChildren, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(minChildren, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel14)
                            .addComponent(jLabel13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(maxReproduceAge, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
                            .addComponent(minReproduceAge)))
                    .addComponent(isCannibal))
                .addContainerGap(396, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(speciesName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(visibility, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(foodLife, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(maxAge, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fleshType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(accessibility, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(minReproduceAge, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(maxReproduceAge, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(parentsRequired, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel16))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(foodRequired, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel17))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(spaceRequired, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(maxChildren, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel23)
                    .addComponent(minChildren, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(spawnProbability, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(spawnInPacks)
                .addGap(18, 18, 18)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(isCannibal)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(baseSeekComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel20)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(graphicBehaviorName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(browseGraphicBehavior))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(saveOrganismConfiguration)
                .addGap(28, 28, 28))
        );

        jScrollPane3.setViewportView(jPanel2);

        jTabbedPane1.addTab("Organisms", jScrollPane3);

        simulate.setText("Simulate!");
        simulate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                simulateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE)
                    .addComponent(simulate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addComponent(simulate)
                .addContainerGap())
        );

        jTabbedPane1.getAccessibleContext().setAccessibleName("World");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * Browse to different graphical behavior.
     *
     * @param evt awt event.
     */
    private void browseGraphicBehaviorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_browseGraphicBehaviorActionPerformed
        GraphicBehaviorConfiguration graphicConfig = new GraphicBehaviorConfiguration(this);
        graphicConfig.setModal(true);
        graphicConfig.setVisible(true);
        graphicBehavior = graphicConfig.getGraphicBehavior();
        graphicBehaviorName.setText(graphicBehavior.toString());
    }//GEN-LAST:event_browseGraphicBehaviorActionPerformed

    /**
     * When selected creature in listbox changes.
     *
     * @param evt awt event.
     */
    private void creaturesSetValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_creaturesSetValueChanged
        OrganismConfiguration o = creaturesSet.getSelectedValue();

        speciesName.setText(o.getSpeciesName());
        baseSeekComboBoxModel.setSelectedItem(o.getBaseSeekBehavior());

        visibility.setValue(o.getVisibility());
        foodLife.setValue(o.getFoodLife());
        maxAge.setValue(o.getMaxAge());
        fleshType.getModel().setSelectedItem(o.getFlesh());

        minReproduceAge.setValue(o.getMinReproduceAge());
        maxReproduceAge.setValue(o.getMaxReproduceAge());
        parentsRequired.setValue(o.getParentsRequired());
        foodRequired.setValue(o.getFoodRequired());
        spaceRequired.setValue(o.getSpaceRequired());
        minChildren.setValue(o.getMinChildren());
        maxChildren.setValue(o.getMaxChildren());

        spawnInPacks.setSelected(o.isSpawnInPacks());
        spawnProbability.setValue((int) (o.getSpawnProbability() * 100));

        for (JCheckBox b : consumables.values()) {
            b.setSelected(false);
        }

        for (Flesh c : o.getConsumes()) {
            consumables.get(c).setSelected(true);
        }

        isCannibal.setSelected(o.isCannibal());

        for (JCheckBox b : accessibles.values()) {
            b.setSelected(false);
        }

        for (TraverseStratedgy s : o.getStratedgies()) {
            accessibles.get(s).setSelected(true);
        }

        accessibility.setValue(o.getAccessibilityRadius());
        graphicBehavior = o.getGraphicBehavior();
        graphicBehaviorName.setText(o.getGraphicBehavior().toString());
    }//GEN-LAST:event_creaturesSetValueChanged

    /**
     * When orgnaism configuration is saved.
     * @param evt awt event.
     */
    private void saveOrganismConfigurationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveOrganismConfigurationActionPerformed
        OrganismConfiguration o = new OrganismConfiguration();

        o.setSpeciesName(speciesName.getText());
        o.setBaseSeekBehavior((BaseSeekBehavior) baseSeekComboBox.getSelectedItem());

        o.setVisibility((Integer) visibility.getValue());
        o.setFoodLife((Integer) foodLife.getValue());
        o.setMaxAge((Integer) maxAge.getValue());
        o.setFlesh((Flesh) fleshType.getSelectedItem());

        o.setMinReproduceAge((Integer) minReproduceAge.getValue());
        o.setMaxReproduceAge((Integer) maxReproduceAge.getValue());
        o.setParentsRequired((Integer) parentsRequired.getValue());
        o.setFoodRequired((Integer) foodRequired.getValue());
        o.setSpaceRequired((Integer) spaceRequired.getValue());
        o.setMinChildren((Integer) minChildren.getValue());
        o.setMaxChildren((Integer) maxChildren.getValue());

        o.setSpawnInPacks(spawnInPacks.isSelected());
        o.setSpawnProbability(spawnProbability.getValue() / 100.0F);

        ArrayList<Flesh> consume = new ArrayList<>();
        for (Map.Entry<Flesh, JCheckBox> b : consumables.entrySet()) {
            if (b.getValue().isSelected()) {
                consume.add(b.getKey());
            }
        }

        o.setConsumes(consume.toArray(new Flesh[consume.size()]));
        o.setIsCannibal(isCannibal.isSelected());

        ArrayList<TraverseStratedgy> traverse = new ArrayList<>();
        for (Map.Entry<TraverseStratedgy, JCheckBox> b : accessibles.entrySet()) {
            if (b.getValue().isSelected()) {
                traverse.add(b.getKey());
            }

        }

        o.setStratedgies(traverse.toArray(new TraverseStratedgy[traverse.size()]));

        o.setAccessibilityRadius((Integer) accessibility.getValue());
        o.setGraphicBehavior(graphicBehavior);

        int i;
        for (i = 0; i < creaturesSetModel.getSize(); i++) {
            if (creaturesSetModel.getElementAt(i).getSpeciesName().equals(o.getSpeciesName())) {
                creaturesSetModel.setElementAt(o, i);
                break;
            }
        }

        if (i == creaturesSetModel.getSize()) {
            creaturesSetModel.addElement(o);
        }
    }//GEN-LAST:event_saveOrganismConfigurationActionPerformed

    /**
     * When the user presses for the simulation to behin.
     * @param evt 
     */
    private void simulateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_simulateActionPerformed
        this.setVisible(false);
    }//GEN-LAST:event_simulateActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSpinner accessibility;
    private javax.swing.JPanel accessiblePanel;
    private javax.swing.JCheckBox autoPlaySimulation;
    private javax.swing.JComboBox baseSeekComboBox;
    private javax.swing.JButton browseGraphicBehavior;
    private javax.swing.JPanel consumablesPanel;
    private javax.swing.JList<OrganismConfiguration> creaturesSet;
    private javax.swing.JComboBox<Flesh> fleshType;
    private javax.swing.JSpinner foodLife;
    private javax.swing.JSpinner foodRequired;
    private javax.swing.JTextField graphicBehaviorName;
    private javax.swing.JCheckBox isCannibal;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JSpinner maxAge;
    private javax.swing.JSpinner maxChildren;
    private javax.swing.JSpinner maxReproduceAge;
    private javax.swing.JSpinner minChildren;
    private javax.swing.JSpinner minReproduceAge;
    private javax.swing.JSpinner numberSimulates;
    private javax.swing.JSpinner parentsRequired;
    private javax.swing.JButton saveOrganismConfiguration;
    private javax.swing.JButton simulate;
    private javax.swing.JSpinner spaceRequired;
    private javax.swing.JCheckBox spawnInPacks;
    private javax.swing.JSlider spawnProbability;
    private javax.swing.JTextField speciesName;
    private javax.swing.JSpinner visibility;
    private javax.swing.JSlider waterProbability;
    private javax.swing.JSlider worldSize;
    // End of variables declaration//GEN-END:variables
}
