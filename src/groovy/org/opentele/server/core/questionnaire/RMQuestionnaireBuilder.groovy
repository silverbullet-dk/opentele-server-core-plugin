package org.opentele.server.core.questionnaire

import org.opentele.server.core.model.QuestionnaireUtil
import org.opentele.server.core.model.types.*
import org.opentele.server.model.HelpImage
import org.opentele.server.model.MeterType
import org.opentele.server.model.questionnaire.*
import org.opentele.server.util.HelpImageUtil

/**
 * User: henrik
 * Date: 26.01.13
 * Time: 20.38
 *
 * Helper class used for creating questionnaires for RM prod environment
 */
class RMQuestionnaireBuilder {

    QuestionnaireUtil questionnaireUtil = new QuestionnaireUtil()

    String createdByName

    // RM Prod questionnaires

    def createRMProdTemperatureQuestionnaire(String title, String revision, Severity omitSeverity) {

        Questionnaire questionnaire = Questionnaire.findByNameAndRevision(title, revision)
        if (!questionnaire) {

            def nodes = []

            EndNode endNode = questionnaireUtil.createEndNode(createdBy: createdByName, modifiedBy: createdByName);
            nodes << endNode

            TextNode contactNode = questionnaireUtil.createTextNode(defaultNext:endNode,
                    text: "Ring til jordemoder", createdBy: createdByName, modifiedBy: createdByName)
            nodes << contactNode

            InputNode moreThanNormalNode = questionnaireUtil.createInputNode(text:"Højere end vanligt?",
                    inputType: DataType.BOOLEAN,
                    defaultNext: contactNode,
                    defaultSeverity: Severity.RED,
                    alternativeNext: endNode,
                    alternativeSeverity: Severity.YELLOW,
                    createdBy: createdByName, modifiedBy: createdByName)
            nodes << moreThanNormalNode

            ChoiceNode tempHighChoiceNode = questionnaireUtil.createChoiceNode(operation: Operation.LESS_THAN,
                    dataType: DataType.FLOAT,
                    alternativeNext: moreThanNormalNode, // 38 >= X (Below or eq)
                    alternativeSeverity: Severity.GREEN,
                    defaultNext: contactNode, // 38 < X (above)
                    defaultSeverity: Severity.RED,
                    nodeValue: 38.0,
                    createdBy: createdByName, modifiedBy: createdByName)
            nodes << tempHighChoiceNode

            ChoiceNode tempNormalChoiceNode = questionnaireUtil.createChoiceNode(operation: Operation.LESS_THAN,
                    dataType: DataType.FLOAT,
                    alternativeNext: endNode, // 37.5 >= X (Below or eq)
                    alternativeSeverity: Severity.GREEN,
                    defaultNext: tempHighChoiceNode, // 37.5 < X (above)
                    defaultSeverity: Severity.YELLOW,
                    nodeValue: 37.500,
                    createdBy: createdByName, modifiedBy: createdByName)
            nodes << tempNormalChoiceNode

            MeasurementNode tNode = questionnaireUtil.createMeasurementNode(text: "Indtast svaret på din temperaturmåling",
                    inputType: DataType.FLOAT,
                    defaultSeverity: Severity.GREEN,
                    defaultNext:tempNormalChoiceNode,
                    nextFail:endNode,
                    nextFailSeverity:omitSeverity,
                    simulate: false,
                    mapToInputFields: true,
                    shortText: "Temperatur",
                    meterType: MeterType.findByName(MeterTypeName.TEMPERATURE),
                    createdBy: createdByName, modifiedBy: createdByName)
            nodes << tNode

            String helpText = "Din temperatur måles rektalt. Husk at bruge de udleverede plastikhylstre af hygiejnegrunde.\n" +
                    "Målingen er færdig, når termometeret bipper."
            tNode.helpInfo = new HelpInfo(text: helpText, questionnaireNode: tNode)

            tempHighChoiceNode.setInputNode(tNode)
            tempHighChoiceNode.setInputVar("FIELD")
            tempHighChoiceNode.save(failOnError:true)

            tempNormalChoiceNode.setInputNode(tNode)
            tempNormalChoiceNode.setInputVar("FIELD")
            tempNormalChoiceNode.save(failOnError:true)

            questionnaire = questionnaireUtil.createQuestionnaire(name: title,
                    revision:revision,
                    startNode: tNode,
                    creationDate: new Date(),
                    createdBy: createdByName, modifiedBy: createdByName)

            nodes.reverseEach() { node ->
                questionnaire.addToNodes(node)
            }
            questionnaire.save(failOnError:true)
        }
        questionnaire
    }

    def createRMProdBloodpressureAndPulseQuestionnaire(String title, String revision, Severity omitSeverity) {

        Questionnaire questionnaire = Questionnaire.findByNameAndRevision(title, revision)
        if (!questionnaire) {

            def nodes = []

            EndNode endNode = questionnaireUtil.createEndNode(createdBy: createdByName, modifiedBy: createdByName);
            nodes << endNode

            TextNode contactNode = questionnaireUtil.createTextNode(defaultNext:endNode,
                    text: "Ring til jordemoder", createdBy: createdByName, modifiedBy: createdByName)
            nodes << contactNode

            InputNode moreThanNormalNode = questionnaireUtil.createInputNode(text:"Er puls højere end vanligt?",
                    inputType: DataType.BOOLEAN,
                    defaultNext: contactNode,
                    defaultSeverity: Severity.RED,
                    alternativeNext: endNode,
                    alternativeSeverity: Severity.YELLOW,
                    createdBy: createdByName, modifiedBy: createdByName)
            nodes << moreThanNormalNode

            ChoiceNode highChoiceNode = questionnaireUtil.createChoiceNode(operation: Operation.LESS_THAN,
                    dataType: DataType.INTEGER,
                    defaultNext: contactNode,
                    defaultSeverity: Severity.RED,
                    alternativeNext: moreThanNormalNode,
                    alternativeSeverity: Severity.GREEN,
                    nodeValue: 105,
                    createdBy: createdByName, modifiedBy: createdByName)
            nodes << highChoiceNode

            ChoiceNode normalChoiceNode = questionnaireUtil.createChoiceNode(operation: Operation.LESS_THAN,
                    dataType: DataType.INTEGER,
                    defaultNext: highChoiceNode, // 85
                    defaultSeverity: Severity.YELLOW,
                    alternativeNext: endNode, // 85
                    alternativeSeverity: Severity.GREEN,
                    nodeValue: 85,
                    createdBy: createdByName, modifiedBy: createdByName)
            nodes << normalChoiceNode

            MeasurementNode measurementNode = questionnaireUtil.createMeasurementNode(text: "Indtast blodtryk og puls",
                    inputType: DataType.INTEGER,
                    defaultSeverity: Severity.GREEN,
                    defaultNext:normalChoiceNode,
                    nextFail:endNode,
                    nextFailSeverity:omitSeverity,
                    simulate: false,
                    mapToInputFields: true,
                    shortText: "Blodtryk/puls",
                    meterType: MeterType.findByName(MeterTypeName.BLOOD_PRESSURE_PULSE),
                    createdBy: createdByName, modifiedBy: createdByName)
            nodes << measurementNode

            String helpText = "Blodtryk tages på venstre arm i siddende position og efter 5 min hvile. \n" +
                    "Manchetten skal sidde, så slangen fra apparatet befinder sig på indersiden af arm. Underkanten af manchetten bør være et par cm fra albuebøjningen.\n" +
                    "Det er vigtigt, at du forholder dig i ro og ikke taler under målingen. Sid heller ikke med benene over kors."
            HelpImage helpImage = HelpImageUtil.ensureHelpImageExists("BT4.jpg")
            measurementNode.helpInfo = new HelpInfo(text: helpText, helpImage: helpImage, questionnaireNode: measurementNode)

            highChoiceNode.setInputNode(measurementNode)
            highChoiceNode.setInputVar(MeasurementNode.PULSE_VAR)
            highChoiceNode.save(failOnError:true)

            normalChoiceNode.setInputNode(measurementNode)
            normalChoiceNode.setInputVar(MeasurementNode.PULSE_VAR)
            normalChoiceNode.save(failOnError:true)

            questionnaire = questionnaireUtil.createQuestionnaire(name: title,
                    revision:revision,
                    startNode: measurementNode,
                    creationDate: new Date(),
                    createdBy: createdByName, modifiedBy: createdByName)

            nodes.reverseEach() { node ->
                questionnaire.addToNodes(node)
            }
            questionnaire.save(failOnError:true)
        }
        questionnaire
    }

    def createRMProdBloodpressureQuestionnaire(String title, boolean mapToInputFields, String revision, Severity omitSeverity) {

        Questionnaire questionnaire = Questionnaire.findByNameAndRevision(title, revision)
        if (!questionnaire) {

            def nodes = []

            EndNode endNode = questionnaireUtil.createEndNode(createdBy: createdByName, modifiedBy: createdByName);
            nodes << endNode

            TextNode contactNode = questionnaireUtil.createTextNode(defaultNext:endNode,
                    text: "Ring til jordemoder", createdBy: createdByName, modifiedBy: createdByName)
            nodes << contactNode

            InputNode moreThanNormalNode = questionnaireUtil.createInputNode(text:"Er blodtrykket højere end vanligt?",
                    inputType: DataType.BOOLEAN,
                    defaultNext: contactNode,
                    defaultSeverity: Severity.RED,
                    alternativeNext: endNode,
                    alternativeSeverity: Severity.YELLOW,
                    createdBy: createdByName, modifiedBy: createdByName)
            nodes << moreThanNormalNode

            ChoiceNode highChoiceNode = questionnaireUtil.createChoiceNode(operation: Operation.LESS_THAN,
                    dataType: DataType.INTEGER,
                    defaultNext: contactNode, // 110 >= X (Below or eq)
                    defaultSeverity: Severity.RED,
                    alternativeNext: moreThanNormalNode, // 110 < X (above)
                    alternativeSeverity: Severity.GREEN,
                    nodeValue: 110,
                    createdBy: createdByName, modifiedBy: createdByName)
            nodes << highChoiceNode

            ChoiceNode normalChoiceNode = questionnaireUtil.createChoiceNode(operation: Operation.LESS_THAN,
                    dataType: DataType.INTEGER,
                    defaultNext: highChoiceNode, // 90 >= X (Below or eq)
                    defaultSeverity: Severity.YELLOW,
                    alternativeNext: endNode, // 90 < X (above)
                    alternativeSeverity: Severity.GREEN,
                    nodeValue: 90,
                    createdBy: createdByName, modifiedBy: createdByName)
            nodes << normalChoiceNode

            MeasurementNode measurementNode = questionnaireUtil.createMeasurementNode(text: "Indtast blodtryk og puls",
                    inputType: DataType.INTEGER,
                    defaultSeverity: Severity.GREEN,
                    defaultNext:normalChoiceNode,
                    nextFail:endNode,
                    nextFailSeverity: omitSeverity,
                    simulate: false,
                    mapToInputFields: mapToInputFields,
                    shortText: "Blodtryk/puls",
                    meterType: MeterType.findByName(MeterTypeName.BLOOD_PRESSURE_PULSE),
                    createdBy: createdByName, modifiedBy: createdByName)
            nodes << measurementNode

            String helpText = "Blodtryk tages på venstre arm i siddende position og efter 5 min hvile. \n" +
                    "Manchetten skal sidde, så slangen fra apparatet befinder sig på indersiden af arm. Underkanten af manchetten bør være et par cm fra albuebøjningen.\n" +
                    "Det er vigtigt, at du forholder dig i ro og ikke taler under målingen. Sid heller ikke med benene over kors."
            HelpImage helpImage = HelpImageUtil.ensureHelpImageExists("BT4.jpg")

            TextNode instruksMaalBlodtryk
            if (!mapToInputFields){
                instruksMaalBlodtryk = questionnaireUtil.createTextNode(defaultNext:measurementNode,
                        text:"Sæt manchetten på armen og tryk på næste når du er klar.", createdBy: createdByName, modifiedBy: createdByName)
                nodes << instruksMaalBlodtryk

                instruksMaalBlodtryk.helpInfo = new HelpInfo(text: helpText, helpImage: helpImage, questionnaireNode: instruksMaalBlodtryk)
            } else {
                measurementNode.helpInfo = new HelpInfo(text: helpText, helpImage: helpImage, questionnaireNode: measurementNode)
            }

            highChoiceNode.setInputNode(measurementNode)
            highChoiceNode.setInputVar(MeasurementNode.DIASTOLIC_VAR)
            highChoiceNode.save(failOnError:true)

            normalChoiceNode.setInputNode(measurementNode)
            normalChoiceNode.setInputVar(MeasurementNode.DIASTOLIC_VAR)
            normalChoiceNode.save(failOnError:true)

            questionnaire = questionnaireUtil.createQuestionnaire(name: title,
                    revision:revision,
                    startNode: (mapToInputFields ? measurementNode : instruksMaalBlodtryk),
                    creationDate: new Date(),
                    createdBy: createdByName, modifiedBy: createdByName)

            nodes.reverseEach() { node ->
                questionnaire.addToNodes(node)
            }
            questionnaire.save(failOnError:true)
        }
        questionnaire
    }

    def createRMProdUrineQuestionnaire(String title, String revision, Severity omitSeverity) {

        Questionnaire questionnaire = Questionnaire.findByNameAndRevision(title, revision)
        if (!questionnaire) {

            def nodes = []

            EndNode endNode = questionnaireUtil.createEndNode(createdBy: createdByName, modifiedBy: createdByName);
            nodes << endNode

            TextNode contactNode = questionnaireUtil.createTextNode(defaultNext:endNode,
                    text: "Ring til jordemoder", createdBy: createdByName, modifiedBy: createdByName)
            nodes << contactNode

            ChoiceNode highChoiceNode = questionnaireUtil.createChoiceNode(operation: Operation.LESS_THAN,
                    dataType: DataType.INTEGER,
                    defaultNext: contactNode,
                    defaultSeverity: Severity.RED,
                    alternativeNext: endNode,
                    alternativeSeverity: Severity.YELLOW,
                    nodeValue: ProteinValue.PLUS_ONE.ordinal(),
                    createdBy: createdByName, modifiedBy: createdByName)
            nodes << highChoiceNode

            ChoiceNode normalChoiceNode = questionnaireUtil.createChoiceNode(operation: Operation.LESS_THAN,
                    dataType: DataType.INTEGER,
                    defaultNext: highChoiceNode,
                    defaultSeverity: Severity.YELLOW,
                    alternativeNext: endNode,
                    alternativeSeverity: Severity.GREEN,
                    nodeValue: ProteinValue.PLUSMINUS.ordinal(),
                    createdBy: createdByName, modifiedBy: createdByName)
            nodes << normalChoiceNode

            MeasurementNode measurementNode = questionnaireUtil.createMeasurementNode(text: "Indtast svaret på din urinundersøgelse (protein)",
                    inputType: DataType.FLOAT,
                    defaultSeverity: Severity.GREEN,
                    defaultNext:normalChoiceNode,
                    nextFail:endNode,
                    nextFailSeverity:omitSeverity,
                    simulate: false,
                    mapToInputFields: true,
                    shortText: "Proteinindhold i urin",
                    meterType: MeterType.findByName(MeterTypeName.URINE),
                    createdBy: createdByName, modifiedBy: createdByName)
            nodes << measurementNode

            String helpText = "Første mængde urin afleveres i toilettet, dernæst en lille mængde i bægeret og resterende urin i toilettet. \n" +
                    "Stiksen dyppes i urinen og tages op med det samme. Afvent præcis 60 sek. og aflæs derefter evt. farveændring på stiksen ud for protein. Glukose har ikke relevans i denne forbindelse."
            HelpImage helpImage = HelpImageUtil.ensureHelpImageExists("Urin4 m rettelser.jpg")
            measurementNode.helpInfo = new HelpInfo(text: helpText, helpImage: helpImage, questionnaireNode: measurementNode)

            highChoiceNode.setInputNode(measurementNode)
            highChoiceNode.setInputVar(MeasurementNode.URINE_VAR)
            highChoiceNode.save(failOnError:true)

            normalChoiceNode.setInputNode(measurementNode)
            normalChoiceNode.setInputVar(MeasurementNode.URINE_VAR)
            normalChoiceNode.save(failOnError:true)

            questionnaire = questionnaireUtil.createQuestionnaire(name: title,
                    revision:revision,
                    startNode: measurementNode,
                    creationDate: new Date(),
                    createdBy: createdByName, modifiedBy: createdByName)

            nodes.reverseEach() { node ->
                questionnaire.addToNodes(node)
            }
            questionnaire.save(failOnError:true)
        }
        questionnaire
    }

    def createRMProdCRPQuestionnaire(String title, String revision, Severity omitSeverity) {

        Questionnaire questionnaire = Questionnaire.findByNameAndRevision(title, revision)
        if (!questionnaire) {

            def nodes = []

            EndNode endNode = questionnaireUtil.createEndNode(createdBy: createdByName, modifiedBy: createdByName);
            nodes << endNode

            TextNode contactNode = questionnaireUtil.createTextNode(defaultNext:endNode,
                    text: "Ring til jordemoder", createdBy: createdByName, modifiedBy: createdByName)
            nodes << contactNode

            ChoiceNode highChoiceNode = questionnaireUtil.createChoiceNode(operation: Operation.GREATER_THAN,
                    dataType: DataType.INTEGER,
                    defaultNext: endNode,
                    defaultSeverity: Severity.YELLOW,
                    alternativeNext: contactNode,
                    alternativeSeverity: Severity.RED,
                    nodeValue: 10,
                    createdBy: createdByName, modifiedBy: createdByName)
            nodes << highChoiceNode

            ChoiceNode normalChoiceNode = questionnaireUtil.createChoiceNode(operation: Operation.GREATER_THAN,
                    dataType: DataType.INTEGER,
                    defaultNext: endNode,
                    defaultSeverity: Severity.GREEN,
                    alternativeNext: highChoiceNode,
                    alternativeSeverity: Severity.YELLOW,
                    nodeValue: 6,
                    createdBy: createdByName, modifiedBy: createdByName)
            nodes << normalChoiceNode

            MeasurementNode measurementNode = questionnaireUtil.createMeasurementNode(text: "Indtast værdi eller vælg <5",
                    inputType: DataType.INTEGER,
                    defaultSeverity: Severity.GREEN,
                    defaultNext:normalChoiceNode,
                    nextFail:endNode,
                    nextFailSeverity:omitSeverity,
                    simulate: false,
                    mapToInputFields: true,
                    shortText: "CRP",
                    meterType: MeterType.findByName(MeterTypeName.CRP),
                    createdBy: createdByName, modifiedBy: createdByName)

            String helpText = "For yderligere vejledning se i folderen, du har fået udleveret."
            measurementNode.helpInfo = new HelpInfo(text: helpText, questionnaireNode: measurementNode)

            nodes << measurementNode

            highChoiceNode.setInputNode(measurementNode)
            highChoiceNode.setInputVar(MeasurementNode.DIASTOLIC_VAR)
            highChoiceNode.save(failOnError:true)

            normalChoiceNode.setInputNode(measurementNode)
            normalChoiceNode.setInputVar(MeasurementNode.DIASTOLIC_VAR)
            normalChoiceNode.save(failOnError:true)

            questionnaire = questionnaireUtil.createQuestionnaire(name: title,
                    revision:revision,
                    startNode: measurementNode,
                    creationDate: new Date(),
                    createdBy: createdByName, modifiedBy: createdByName)

            nodes.reverseEach() { node ->
                questionnaire.addToNodes(node)
            }
            questionnaire.save(failOnError:true)
        }
        questionnaire
    }

    def createPPROMQuestionnaire(String title, String revision, Severity omitSeverity) {

        Questionnaire questionnaire = Questionnaire.findByNameAndRevision(title, revision)
        if (!questionnaire) {

            def nodes = []

            String contactMidwifeVar = "CONTACT_midwife"

            EndNode endNode = questionnaireUtil.createEndNode(createdBy: createdByName, modifiedBy: createdByName);
            nodes << endNode

            TextNode contactMidwifeNode = questionnaireUtil.createTextNode(defaultNext:endNode,
                    text:"Ring til jordemoder", createdBy: createdByName, modifiedBy: createdByName)
            nodes << contactMidwifeNode

            ChoiceNode contactChoiceNode = questionnaireUtil.createChoiceNode(operation: Operation.EQUALS,
                    dataType: DataType.BOOLEAN,
                    defaultNext: contactMidwifeNode,
                    defaultSeverity: Severity.GREEN,
                    alternativeNext: endNode,
                    alternativeSeverity: Severity.GREEN,
                    nodeValue: true,
                    createdBy: createdByName, modifiedBy: createdByName)
            nodes << contactChoiceNode

            BooleanNode contactNode6 = questionnaireUtil.createBooleanNode(defaultNext:contactChoiceNode,
                    variableName:contactMidwifeVar,
                    value:true,
                    createdBy: createdByName, modifiedBy: createdByName)
            nodes << contactNode6

            contactChoiceNode.setInputNode(contactNode6);
            contactChoiceNode.setInputVar(contactMidwifeVar)
            contactChoiceNode.save(failOnError:true)

            InputNode movementNode = questionnaireUtil.createInputNode(text:"Bevæger barnet sig som det plejer?",
                    inputType: DataType.BOOLEAN,
                    defaultNext: contactChoiceNode,
                    defaultSeverity: Severity.GREEN,
                    alternativeNext: contactNode6,
                    alternativeSeverity: Severity.RED,
                    createdBy: createdByName, modifiedBy: createdByName)
            nodes << movementNode

            String movementHelpText = "I en normal graviditet vil barnet både have perioder hvor det bevæger sig og stille perioder, da børn også har søvnperioder inden fødslen. Du skal svare nej, hvis du synes dit barns bevægemønster/hyppigheden af bevægelser har ændret sig markant i forhold til hvad du er vant til."
            movementNode.helpInfo = new HelpInfo(text: movementHelpText, questionnaireNode: movementNode)

            InputNode weeksNode = questionnaireUtil.createInputNode(text:"Er du over uge 26?",
                    inputType: DataType.BOOLEAN,
                    defaultNext: movementNode,
                    defaultSeverity: Severity.GREEN,
                    alternativeNext: contactChoiceNode,
                    alternativeSeverity: Severity.GREEN,
                    createdBy: createdByName, modifiedBy: createdByName)
            nodes << weeksNode

            // Tyngde

            BooleanNode contactNode5 = questionnaireUtil.createBooleanNode(defaultNext:weeksNode,
                    variableName:contactMidwifeVar,
                    value:true,
                    createdBy: createdByName, modifiedBy: createdByName)
            nodes << contactNode5

            InputNode tyngdeMoreNode = questionnaireUtil.createInputNode(text:"Mere end vanligt?",
                    inputType: DataType.BOOLEAN,
                    defaultNext: contactNode5,
                    defaultSeverity: Severity.RED,
                    alternativeNext: weeksNode,
                    alternativeSeverity: Severity.YELLOW,
                    shortText: "Mere tyngde",
                    createdBy: createdByName, modifiedBy: createdByName)
            nodes << tyngdeMoreNode

            InputNode tyngdeNode = questionnaireUtil.createInputNode(text:"Har du tyngdefornemmelse?",
                    inputType: DataType.BOOLEAN,
                    defaultNext: tyngdeMoreNode,
                    defaultSeverity: Severity.YELLOW,
                    alternativeNext: weeksNode,
                    alternativeSeverity: Severity.GREEN,
                    shortText: "Tyngde",
                    createdBy: createdByName, modifiedBy: createdByName)
            nodes << tyngdeNode

            String tyngdeHelpText = "Tyngdefornemmelse kan være en fornemmelse i skeden, at barnet tynger ned i skeden eller ligger dybere/længere nede end det plejer."
            tyngdeNode.helpInfo = new HelpInfo(text: tyngdeHelpText, questionnaireNode: tyngdeNode)

            // Plukkeveer

            BooleanNode contactNode4 = questionnaireUtil.createBooleanNode(defaultNext:tyngdeNode,
                    variableName:contactMidwifeVar,
                    value:true,
                    createdBy: createdByName, modifiedBy: createdByName)
            nodes << contactNode4

            InputNode plukkeMereNode = questionnaireUtil.createInputNode(text:"Flere end vanligt?",
                    inputType: DataType.BOOLEAN,
                    defaultNext: contactNode4,
                    defaultSeverity: Severity.RED,
                    alternativeNext: tyngdeNode,
                    alternativeSeverity: Severity.YELLOW,
                    shortText: "Flere plukkeveer",
                    createdBy: createdByName, modifiedBy: createdByName)
            nodes << plukkeMereNode

            String plukkeMereHelpText = "Plukkeveer er en normal del af graviditeten og kan øges ved fx fysisk aktivitet. Her vil vi gerne vide, om du har flere plukkeveer, om de er blevet kraftigere og/eller om de er blevet regelmæssige."
            plukkeMereNode.helpInfo = new HelpInfo(text: plukkeMereHelpText, questionnaireNode: plukkeMereNode)

            InputNode plukkeNode = questionnaireUtil.createInputNode(text:"Har du plukkeveer?",
                    inputType: DataType.BOOLEAN,
                    defaultNext: plukkeMereNode,
                    defaultSeverity: Severity.YELLOW,
                    alternativeNext: tyngdeNode,
                    alternativeSeverity: Severity.GREEN,
                    shortText: "Plukkeveer",
                    createdBy: createdByName, modifiedBy: createdByName)
            nodes << plukkeNode

            // Mave/livmoder øm?

            BooleanNode contactNode3 = questionnaireUtil.createBooleanNode(defaultNext:plukkeNode,
                    variableName:contactMidwifeVar,
                    value:true,
                    createdBy: createdByName, modifiedBy: createdByName)
            nodes << contactNode3

            InputNode stomachNode = questionnaireUtil.createInputNode(text:"Er din mave/livmoder øm?",
                    inputType: DataType.BOOLEAN,
                    defaultNext: contactNode3,
                    defaultSeverity: Severity.RED,
                    alternativeNext: plukkeNode,
                    alternativeSeverity: Severity.GREEN,
                    createdBy: createdByName, modifiedBy: createdByName)
            nodes << stomachNode

            String stomachHelpText = "Er din mave/livmoder mere øm end den plejer? Ømheden kan være konstant eller komme ved berøring. Ømheden vil oftest ikke sidde et specifikt sted. Det er ”hele” mave/livmoderen, der er øm."
            stomachNode.helpInfo = new HelpInfo(text: stomachHelpText, questionnaireNode: stomachNode)

            // Temperatur

            BooleanNode contactNode2 = questionnaireUtil.createBooleanNode(defaultNext:stomachNode,
                    variableName:contactMidwifeVar,
                    value:true,
                    createdBy: createdByName, modifiedBy: createdByName)
            nodes << contactNode2

            InputNode moreThanNormalNode = questionnaireUtil.createInputNode(text:"Er temperaturen højere end vanligt?",
                    inputType: DataType.BOOLEAN,
                    defaultNext: contactNode2,
                    defaultSeverity: Severity.RED,
                    alternativeNext: stomachNode,
                    alternativeSeverity: Severity.YELLOW,
                    createdBy: createdByName, modifiedBy: createdByName)
            nodes << moreThanNormalNode

            ChoiceNode tempHighChoiceNode = questionnaireUtil.createChoiceNode(operation: Operation.LESS_THAN,
                    dataType: DataType.FLOAT,
                    alternativeNext: moreThanNormalNode, // 38 >= X (Below or eq)
                    alternativeSeverity: Severity.GREEN,
                    defaultNext: contactNode2, // 38 < X (above)
                    defaultSeverity: Severity.RED,
                    nodeValue: 38.0,
                    createdBy: createdByName, modifiedBy: createdByName)
            nodes << tempHighChoiceNode

            ChoiceNode tempNormalChoiceNode = questionnaireUtil.createChoiceNode(operation: Operation.LESS_THAN,
                    dataType: DataType.FLOAT,
                    alternativeNext: stomachNode, // 37.5 >= X (Below or eq)
                    alternativeSeverity: Severity.GREEN,
                    defaultNext: tempHighChoiceNode, // 37.5 < X (above)
                    defaultSeverity: Severity.YELLOW,
                    nodeValue: 37.5,
                    createdBy: createdByName, modifiedBy: createdByName)
            nodes << tempNormalChoiceNode

            MeasurementNode temperatureNode = questionnaireUtil.createMeasurementNode(text: "Indtast svaret på din temperaturmåling",
                    inputType: DataType.FLOAT,
                    defaultSeverity: Severity.GREEN,
                    defaultNext:tempNormalChoiceNode,
                    nextFail:stomachNode,
                    nextFailSeverity:omitSeverity,
                    mapToInputFields: true,
                    shortText: "Temperatur",
                    meterType: MeterType.findByName(MeterTypeName.TEMPERATURE),
                    createdBy: createdByName, modifiedBy: createdByName)
            nodes << temperatureNode

            String temperatureHelpText = "Din temperatur måles rektalt. Husk at bruge de udleverede plastikhylstre af hygiejnegrunde.\n" +
                    "Målingen er færdig, når termometeret bipper."
            temperatureNode.helpInfo = new HelpInfo(text: temperatureHelpText, questionnaireNode: temperatureNode)

            tempHighChoiceNode.setInputNode(temperatureNode)
            tempHighChoiceNode.setInputVar("FIELD")
            tempHighChoiceNode.save(failOnError:true)

            tempNormalChoiceNode.setInputNode(temperatureNode)
            tempNormalChoiceNode.setInputVar("FIELD")
            tempNormalChoiceNode.save(failOnError:true)

            // Blodtryk + puls..

            BooleanNode contactNode1 = questionnaireUtil.createBooleanNode(defaultNext:temperatureNode,
                    variableName:contactMidwifeVar,
                    value:true,
                    createdBy: createdByName, modifiedBy: createdByName)
            nodes << contactNode1

            InputNode pulseMoreThanNormalNode = questionnaireUtil.createInputNode(text:"Er puls højere end vanligt?",
                    inputType: DataType.BOOLEAN,
                    defaultNext: contactNode1,
                    defaultSeverity: Severity.RED,
                    alternativeNext: temperatureNode,
                    alternativeSeverity: Severity.YELLOW,
                    createdBy: createdByName, modifiedBy: createdByName)
            nodes << pulseMoreThanNormalNode

            ChoiceNode highPulseChoiceNode = questionnaireUtil.createChoiceNode(operation: Operation.LESS_THAN,
                    dataType: DataType.INTEGER,
                    defaultNext: contactNode1,
                    defaultSeverity: Severity.RED,
                    alternativeNext: pulseMoreThanNormalNode,
                    alternativeSeverity: Severity.GREEN,
                    nodeValue: 105,
                    createdBy: createdByName, modifiedBy: createdByName)
            nodes << highPulseChoiceNode

            ChoiceNode normalPulseChoiceNode = questionnaireUtil.createChoiceNode(operation: Operation.LESS_THAN,
                    dataType: DataType.INTEGER,
                    defaultNext: highPulseChoiceNode, // 85
                    defaultSeverity: Severity.YELLOW,
                    alternativeNext: temperatureNode, // 85
                    alternativeSeverity: Severity.GREEN,
                    nodeValue: 85,
                    createdBy: createdByName, modifiedBy: createdByName)
            nodes << normalPulseChoiceNode

            MeasurementNode maalBlodtrykNode = questionnaireUtil.createMeasurementNode(text: "Indtast blodtryk og puls",
                    inputType: DataType.INTEGER,
                    defaultSeverity: Severity.GREEN,
                    defaultNext:normalPulseChoiceNode,
                    nextFail:temperatureNode,
                    nextFailSeverity:omitSeverity,
                    mapToInputFields: true,
                    simulate: false,
                    shortText: "Blodtryk/puls",
                    meterType: MeterType.findByName(MeterTypeName.BLOOD_PRESSURE_PULSE),
                    createdBy: createdByName, modifiedBy: createdByName)
            nodes << maalBlodtrykNode

            String maalBlodtrykHelpText = "Blodtryk tages på venstre arm i siddende position og efter 5 min hvile. \n" +
                    "Manchetten skal sidde, så slangen fra apparatet befinder sig på indersiden af arm. Underkanten af manchetten bør være et par cm fra albuebøjningen.\n" +
                    "Det er vigtigt, at du forholder dig i ro og ikke taler under målingen. Sid heller ikke med benene over kors."
            HelpImage helpImage = HelpImageUtil.ensureHelpImageExists("BT4.jpg")
            maalBlodtrykNode.helpInfo = new HelpInfo(text: maalBlodtrykHelpText, helpImage: helpImage, questionnaireNode: maalBlodtrykNode)


            highPulseChoiceNode.setInputNode(maalBlodtrykNode)
            highPulseChoiceNode.setInputVar(MeasurementNode.PULSE_VAR)
            highPulseChoiceNode.save(failOnError:true)

            normalPulseChoiceNode.setInputNode(maalBlodtrykNode)
            normalPulseChoiceNode.setInputVar(MeasurementNode.PULSE_VAR)
            normalPulseChoiceNode.save(failOnError:true)


            BooleanNode initContactMidwifeNode = questionnaireUtil.createBooleanNode(defaultNext: maalBlodtrykNode,
                    variableName:contactMidwifeVar,
                    value:false,
                    createdBy: createdByName, modifiedBy: createdByName)
            nodes << initContactMidwifeNode

            questionnaire = questionnaireUtil.createQuestionnaire(name: title,
                    revision:revision,
                    startNode: initContactMidwifeNode,
                    creationDate: new Date(),
                    createdBy: createdByName, modifiedBy: createdByName)

            nodes.reverseEach() {node ->
                questionnaire.addToNodes(node)
            }

            questionnaire.save(failOnError:true)
        }
        questionnaire
    }

    def createPraeeklampsiOrDiabetesQuestionnaire(String title, String revision, Severity omitSeverity) {

        Questionnaire questionnaire = Questionnaire.findByNameAndRevision(title, revision)
        if (!questionnaire) {

            String contactMidwifeVar = "CONTACT_MIDWIFE"

            def nodes = []

            EndNode endNode = questionnaireUtil.createEndNode(createdBy: createdByName, modifiedBy: createdByName);
            nodes << endNode

            TextNode contactMidwifeNode = questionnaireUtil.createTextNode(defaultNext:endNode,
                    text:"Ring til jordemoder", createdBy: createdByName, modifiedBy: createdByName)
            nodes << contactMidwifeNode

            ChoiceNode contactChoiceNode = questionnaireUtil.createChoiceNode(operation: Operation.EQUALS,
                    dataType: DataType.BOOLEAN,
                    defaultNext: contactMidwifeNode,
                    defaultSeverity: Severity.GREEN,
                    alternativeNext: endNode,
                    alternativeSeverity: Severity.GREEN,
                    nodeValue: true,
                    createdBy: createdByName, modifiedBy: createdByName)
            nodes << contactChoiceNode

            BooleanNode contactNode8 = questionnaireUtil.createBooleanNode(defaultNext:contactChoiceNode,
                    variableName:contactMidwifeVar,
                    value:true,
                    createdBy: createdByName, modifiedBy: createdByName)
            nodes << contactNode8

            contactChoiceNode.setInputNode(contactNode8);
            contactChoiceNode.setInputVar(contactMidwifeVar)
            contactChoiceNode.save(failOnError:true)

            InputNode jMovementNode = questionnaireUtil.createInputNode(text:"Bevæger barnet sig som det plejer?",
                    inputType: DataType.BOOLEAN,
                    defaultNext: contactChoiceNode,
                    defaultSeverity: Severity.GREEN,
                    alternativeNext: contactNode8,
                    alternativeSeverity: Severity.RED,
                    createdBy: createdByName, modifiedBy: createdByName)
            nodes << jMovementNode

            String jMovementHelpText = "I en normal graviditet vil barnet både have perioder hvor det bevæger sig og stille perioder, da børn også har søvnperioder inden fødslen. Du skal svare nej, hvis du synes dit barns bevægemønster/hyppigheden af bevægelser har ændret sig markant i forhold til hvad du er vant til."
            jMovementNode.helpInfo = new HelpInfo(text: jMovementHelpText, questionnaireNode: jMovementNode)

            InputNode week26Node = questionnaireUtil.createInputNode(text:"Er du over uge 26?",
                    inputType: DataType.BOOLEAN,
                    defaultNext: jMovementNode,
                    defaultSeverity: Severity.GREEN,
                    alternativeNext: contactChoiceNode,
                    alternativeSeverity: Severity.GREEN,
                    createdBy: createdByName, modifiedBy: createdByName)
            nodes << week26Node

            BooleanNode contactNode7 = questionnaireUtil.createBooleanNode(defaultNext:week26Node,
                    variableName:contactMidwifeVar,
                    value:true,
                    createdBy: createdByName, modifiedBy: createdByName)
            nodes << contactNode7

            InputNode unwellNode = questionnaireUtil.createInputNode(text:"Er du alment utilpas?",
                    inputType: DataType.BOOLEAN,
                    defaultNext: contactNode7,
                    defaultSeverity: Severity.RED,
                    alternativeNext: week26Node,
                    alternativeSeverity: Severity.GREEN,
                    createdBy: createdByName, modifiedBy: createdByName)
            nodes << unwellNode

            String unwellHelpText = "Alment utilpas kan beskrives som influenza-lignende symptomer eller ”man er bare ikke sig selv”. Nogle kan have svært ved at give en præcis beskrivelse af utilpasheden."
            unwellNode.helpInfo = new HelpInfo(text: unwellHelpText, questionnaireNode: unwellNode)

            BooleanNode contactNode6 = questionnaireUtil.createBooleanNode(defaultNext:unwellNode,
                    variableName:contactMidwifeVar,
                    value:true,
                    createdBy: createdByName, modifiedBy: createdByName)
            nodes << contactNode6

            InputNode breathlessnessNode = questionnaireUtil.createInputNode(text:"Har du åndenød?",
                    inputType: DataType.BOOLEAN,
                    defaultNext: contactNode6,
                    defaultSeverity: Severity.RED,
                    alternativeNext: unwellNode,
                    alternativeSeverity: Severity.GREEN,
                    createdBy: createdByName, modifiedBy: createdByName)
            nodes << breathlessnessNode

            String breathlessnessHelpText = "Her menes en besværet vejrtrækning, som om der ligger noget tungt på din brystkasse."
            breathlessnessNode.helpInfo = new HelpInfo(text: breathlessnessHelpText, questionnaireNode: breathlessnessNode)

            BooleanNode contactNode5 = questionnaireUtil.createBooleanNode(defaultNext:breathlessnessNode,
                    variableName:contactMidwifeVar,
                    value:true,
                    createdBy: createdByName, modifiedBy: createdByName)
            nodes << contactNode5

            InputNode stomachPainNode = questionnaireUtil.createInputNode(text: "Har du smerter i øvre halvdel af maven?",
                    inputType: DataType.BOOLEAN,
                    defaultNext: contactNode5,
                    defaultSeverity: Severity.RED,
                    alternativeNext: breathlessnessNode,
                    alternativeSeverity: Severity.GREEN,
                    createdBy: createdByName, modifiedBy: createdByName)
            nodes << stomachPainNode

            String stomachPainHelpText = "Smerterne sidder typisk under højre ribbenskant, under brystbenet eller er bælteformet over øvre del af maven. Smerterne kan være konstante, eller komme og gå – og nogle har samtidig kvalme eller kaster op."
            stomachPainNode.helpInfo = new HelpInfo(text: stomachPainHelpText, questionnaireNode: stomachPainNode)

            BooleanNode contactNode4 = questionnaireUtil.createBooleanNode(defaultNext:stomachPainNode,
                    variableName:contactMidwifeVar,
                    value:true,
                    createdBy: createdByName, modifiedBy: createdByName)
            nodes << contactNode4

            InputNode eyesFlickerWorseNode = questionnaireUtil.createInputNode(text:"Værre end i går?",
                    inputType: DataType.BOOLEAN,
                    defaultNext: contactNode4,
                    defaultSeverity: Severity.RED,
                    alternativeNext: stomachPainNode,
                    alternativeSeverity: Severity.YELLOW,
                    createdBy: createdByName, modifiedBy: createdByName)
            nodes << eyesFlickerWorseNode

            InputNode eyesFlickerNode = questionnaireUtil.createInputNode(text:"Har du flimren for øjnene?",
                    inputType: DataType.BOOLEAN,
                    defaultNext: eyesFlickerWorseNode,
                    defaultSeverity: Severity.YELLOW,
                    alternativeNext: stomachPainNode,
                    alternativeSeverity: Severity.GREEN,
                    createdBy: createdByName, modifiedBy: createdByName)
            nodes << eyesFlickerNode

            String eyesFlickerHelpText = "Ved flimren for øjnene menes at dit syn er påvirket. Det kan være uklart eller dit syn er dårligere end det plejer at være. Det kommer oftest uprovokeret."
            eyesFlickerNode.helpInfo = new HelpInfo(text: eyesFlickerHelpText, questionnaireNode: eyesFlickerNode)

            BooleanNode contactNode3 = questionnaireUtil.createBooleanNode(defaultNext:eyesFlickerNode,
                    variableName:contactMidwifeVar,
                    value:true,
                    createdBy: createdByName, modifiedBy: createdByName)
            nodes << contactNode3

            InputNode headacheWorseNode = questionnaireUtil.createInputNode(text:"Er hovedpinen værre end i går?",
                    inputType: DataType.BOOLEAN,
                    defaultNext: contactNode3,
                    defaultSeverity: Severity.RED,
                    alternativeNext: eyesFlickerNode,
                    alternativeSeverity: Severity.YELLOW,
                    createdBy: createdByName, modifiedBy: createdByName)
            nodes << headacheWorseNode

            InputNode headacheYesterdayNode = questionnaireUtil.createInputNode(text:"Havde du hovedpine i går?",
                    inputType: DataType.BOOLEAN,
                    defaultNext: headacheWorseNode,
                    defaultSeverity: Severity.YELLOW,
                    alternativeNext: contactNode3,
                    alternativeSeverity: Severity.RED,
                    createdBy: createdByName, modifiedBy: createdByName)
            nodes << headacheYesterdayNode

            InputNode headacheNode = questionnaireUtil.createInputNode(text:"Har du hovedpine?",
                    inputType: DataType.BOOLEAN,
                    defaultNext: headacheYesterdayNode,
                    defaultSeverity: Severity.YELLOW,
                    alternativeNext: eyesFlickerNode,
                    alternativeSeverity: Severity.GREEN,
                    createdBy: createdByName, modifiedBy: createdByName)
            nodes << headacheNode

            String headacheHelpText = "Hovedpinen sidder oftest i panden, evt. omkring øjnene og kan føles som et bånd, der strammer. Man kan godt have hovedpine af andre årsager (fx for lidt søvn eller spændingshovedpine). Du skal svare JA, hvis du har hovedpine, du mener ikke skyldes sådanne andre oplagte årsager."
            headacheNode.helpInfo = new HelpInfo(text: headacheHelpText, questionnaireNode: headacheNode)


            MeasurementNode weightNode = questionnaireUtil.createMeasurementNode(text: "Indtast din vægt",
                    defaultNext:headacheNode,
                    nextFail: headacheNode,
                    nextFailSeverity:omitSeverity,
                    shortText: "Vægt",
                    mapToInputFields: true,
                    meterType: MeterType.findByName(MeterTypeName.WEIGHT),
                    createdBy: createdByName, modifiedBy: createdByName)
            nodes << weightNode

            // Protein / urin
            BooleanNode contactNode2 = questionnaireUtil.createBooleanNode(defaultNext:weightNode,
                    variableName:contactMidwifeVar,
                    value:true,
                    createdBy: createdByName, modifiedBy: createdByName)
            nodes << contactNode2

            ChoiceNode highUrineChoiceNode = questionnaireUtil.createChoiceNode(operation: Operation.LESS_THAN,
                    dataType: DataType.INTEGER,
                    defaultNext: contactNode2,
                    defaultSeverity: Severity.RED,
                    alternativeNext: weightNode,
                    alternativeSeverity: Severity.YELLOW,
                    nodeValue: ProteinValue.PLUS_ONE.ordinal(),
                    createdBy: createdByName, modifiedBy: createdByName)
            nodes << highUrineChoiceNode

            ChoiceNode normalUrineChoiceNode = questionnaireUtil.createChoiceNode(operation: Operation.LESS_THAN,
                    dataType: DataType.INTEGER,
                    defaultNext: highUrineChoiceNode,
                    defaultSeverity: Severity.YELLOW,
                    alternativeNext: weightNode,
                    alternativeSeverity: Severity.GREEN,
                    nodeValue: ProteinValue.PLUSMINUS.ordinal(),
                    createdBy: createdByName, modifiedBy: createdByName)
            nodes << normalUrineChoiceNode

            MeasurementNode urineNode = questionnaireUtil.createMeasurementNode(text: "Indtast svaret på din urinundersøgelse (protein)",
                    inputType: DataType.FLOAT,
                    defaultSeverity: Severity.GREEN,
                    defaultNext:normalUrineChoiceNode,
                    nextFail:weightNode,
                    nextFailSeverity:omitSeverity,
                    mapToInputFields: true,
                    shortText: "Proteinindhold i urin",
                    meterType: MeterType.findByName(MeterTypeName.URINE),
                    createdBy: createdByName, modifiedBy: createdByName)
            nodes << urineNode

            String helpText = "Første mængde urin afleveres i toilettet, dernæst en lille mængde i bægeret og resterende urin i toilettet. \n" +
                    "Stiksen dyppes i urinen og tages op med det samme. Afvent præcis 60 sek. og aflæs derefter evt. farveændring på stiksen ud for protein. Glukose har ikke relevans i denne forbindelse."
            HelpImage helpImage = HelpImageUtil.ensureHelpImageExists("Urin4 m rettelser.jpg")
            urineNode.helpInfo = new HelpInfo(text: helpText, helpImage: helpImage, questionnaireNode: urineNode)

            highUrineChoiceNode.setInputNode(urineNode)
            highUrineChoiceNode.setInputVar(MeasurementNode.URINE_VAR)
            highUrineChoiceNode.save(failOnError:true)

            normalUrineChoiceNode.setInputNode(urineNode)
            normalUrineChoiceNode.setInputVar(MeasurementNode.URINE_VAR)
            normalUrineChoiceNode.save(failOnError:true)

            // Bloodpressure - only check diastolic
            BooleanNode contactNode1 = questionnaireUtil.createBooleanNode(defaultNext:urineNode,
                    variableName:contactMidwifeVar,
                    value:true,
                    createdBy: createdByName, modifiedBy: createdByName)
            nodes << contactNode1

            InputNode moreThanNormalNode = questionnaireUtil.createInputNode(text:"Er blodtrykket højere end vanligt?",
                    inputType: DataType.BOOLEAN,
                    defaultNext: contactNode1,
                    defaultSeverity: Severity.RED,
                    alternativeNext: urineNode,
                    alternativeSeverity: Severity.YELLOW,
                    createdBy: createdByName, modifiedBy: createdByName)
            nodes << moreThanNormalNode

            ChoiceNode highChoiceNode = questionnaireUtil.createChoiceNode(operation: Operation.LESS_THAN,
                    dataType: DataType.INTEGER,
                    defaultNext: contactNode1,
                    defaultSeverity: Severity.RED,
                    alternativeNext: moreThanNormalNode,
                    alternativeSeverity: Severity.GREEN,
                    nodeValue: 110,
                    createdBy: createdByName, modifiedBy: createdByName)
            nodes << highChoiceNode

            ChoiceNode normalChoiceNode = questionnaireUtil.createChoiceNode(operation: Operation.LESS_THAN,
                    dataType: DataType.INTEGER,
                    defaultNext: highChoiceNode,
                    defaultSeverity: Severity.YELLOW,
                    alternativeNext: urineNode,
                    alternativeSeverity: Severity.GREEN,
                    nodeValue: 90,
                    createdBy: createdByName, modifiedBy: createdByName)
            nodes << normalChoiceNode

            MeasurementNode blodtrykNode = questionnaireUtil.createMeasurementNode(text: "Indtast blodtryk og puls",
                    inputType: DataType.INTEGER,
                    defaultSeverity: Severity.GREEN,
                    defaultNext: normalChoiceNode,
                    nextFail: urineNode,
                    nextFailSeverity:omitSeverity,
                    mapToInputFields: true,
                    shortText: "Blodtryk/puls",
                    meterType: MeterType.findByName(MeterTypeName.BLOOD_PRESSURE_PULSE),
                    createdBy: createdByName, modifiedBy: createdByName)
            nodes << blodtrykNode

            String maalBlodtrykHelpText = "Blodtryk tages på venstre arm i siddende position og efter 5 min hvile. \n" +
                    "Manchetten skal sidde, så slangen fra apparatet befinder sig på indersiden af arm. Underkanten af manchetten bør være et par cm fra albuebøjningen.\n" +
                    "Det er vigtigt, at du forholder dig i ro og ikke taler under målingen. Sid heller ikke med benene over kors."
            HelpImage btHelpImage = HelpImageUtil.ensureHelpImageExists("BT4.jpg")
            blodtrykNode.helpInfo = new HelpInfo(text: maalBlodtrykHelpText, helpImage: btHelpImage, questionnaireNode: blodtrykNode)

            highChoiceNode.setInputNode(blodtrykNode)
            highChoiceNode.setInputVar(MeasurementNode.DIASTOLIC_VAR)
            highChoiceNode.save(failOnError:true)

            normalChoiceNode.setInputNode(blodtrykNode)
            normalChoiceNode.setInputVar(MeasurementNode.DIASTOLIC_VAR)
            normalChoiceNode.save(failOnError:true)

            BooleanNode initContactVarNode = questionnaireUtil.createBooleanNode(defaultNext: blodtrykNode,
                    variableName: contactMidwifeVar,
                    value: false,
                    createdBy: createdByName, modifiedBy: createdByName)
            nodes << initContactVarNode

            questionnaire = questionnaireUtil.createQuestionnaire(name: title,
                    revision:revision,
                    startNode: initContactVarNode,
                    creationDate: new Date(),
                    createdBy: createdByName, modifiedBy: createdByName)

            nodes.reverseEach() { node ->
                questionnaire.addToNodes(node)
            }

            questionnaire.save(failOnError:true)
        }
        questionnaire
    }

    /**
     * Create CTG questionnaire, with an added node for determining how many minutes CTG measurement should continue
     */
    def createTimedCTGQuestionnaire(String title, boolean simulate, String revision, Severity omitSeverity) {

        Questionnaire questionnaire = Questionnaire.findByNameAndRevision(title, revision)
        if (!questionnaire) {

            EndNode ctgEndNode = questionnaireUtil.createEndNode(createdBy: createdByName, modifiedBy: createdByName);

            MeterType ctgMeterType = MeterType.findByName(MeterTypeName.CTG)

            MeasurementNode ctgNode = questionnaireUtil.createMeasurementNode(text: "CTG (m/tid)",
                    defaultNext:ctgEndNode,
                    nextFail: ctgEndNode,
                    nextFailSeverity:omitSeverity,
                    meterType: ctgMeterType,
                    shortText: "CTG (m/tid)",
                    simulate: simulate,
                    createdBy: createdByName, modifiedBy: createdByName)

            InputNode maalDitCtg = questionnaireUtil.createInputNode(text: "Gør dig klar til at måle CTG. Placér elektroder på maven og indtast antal minutter målingen skal vare. Tryk næste når du er klar.",
                    defaultNext: ctgNode,
                    inputType: DataType.INTEGER,
                    shortText: "Antal minutter",
                    createdBy: createdByName,
                    modifiedBy: createdByName)

            ctgNode.setMonicaMeasuringTimeInputNode(maalDitCtg)
            ctgNode.setMonicaMeasuringTimeInputVar("FIELD")
            ctgNode.save(failOnError:true)

            questionnaire = questionnaireUtil.createQuestionnaire(name: title,
                    revision:revision,
                    requiresManualInspection: true,
                    startNode: maalDitCtg,
                    creationDate: new Date(),
                    createdBy: createdByName, modifiedBy: createdByName)

            questionnaire.addToNodes(ctgNode)
            questionnaire.addToNodes(maalDitCtg)
            questionnaire.addToNodes(ctgEndNode)

            questionnaire.save(failOnError:true)
        }
        questionnaire
    }
}