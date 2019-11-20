package com.alibaba.idst.nls.dm.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.idst.nls.dm.common.io.NluResultElement;

import lombok.Data;

/**
 * @author niannian.ynn
 */
@Data
public class IntentSequence {
    private final List<NluResultElement> multiRawNluResults = new ArrayList<>();
    private final List<Intent> intents = new ArrayList<>();
    private NluResultElement rawNluResult;
    private double score = 0.0;
    private String sourceType;

    public IntentSequence(NluResultElement rawNluResult) {
        this.rawNluResult = rawNluResult;
    }

    public IntentSequence(NluResultElement rawNluResult, DomainOntology domainOntology) {
        this.rawNluResult = rawNluResult;
        Intent intent = parseIntent(rawNluResult, domainOntology);
        intents.add(intent);
        score = intent.getProbability();
        sourceType = intent.getSource();
    }

    public IntentSequence(List<NluResultElement> nluResultElements, DomainOntology domainOntology) {
        if (!nluResultElements.isEmpty()) {
            this.multiRawNluResults.addAll(nluResultElements);
            this.rawNluResult = nluResultElements.get(0);
            this.score = rawNluResult.getScore();
            this.sourceType = rawNluResult.getSource();
        } else {
            rawNluResult = null;
        }
        for (NluResultElement element : nluResultElements) {
            Intent intent = parseIntent(element, domainOntology);
            intents.add(intent);
        }
    }

    public static Intent parseIntent(NluResultElement element, DomainOntology domainOntology) {
        String domainNname = "";
        String intentName = "";
        if (element.getDomain() != null) {
            domainNname = element.getDomain();
        }
        if (element.getIntent() != null) {
            intentName = element.getIntent();
        }
        IntentDef intentDef = null;
        if (domainOntology != null) {
            intentDef = domainOntology.getIntentByName(domainNname, intentName);
        }
        if (intentDef == null) {
            // create intent def
            intentDef = new IntentDef();
            intentDef.setDomain(element.getDomain());
            intentDef.setIntent(element.getIntent());
            for (String slotName : element.getSlots().keySet()) {
                SlotDef slotDef = new SlotDef();
                slotDef.setName(slotName);
                intentDef.getSlotDefs().add(slotDef);
            }
        } else {
            // 检查slot定义是否与当前domain ontology一致，如果不一致则修复，以nlu的为准
            for (String slotName : element.getSlots().keySet()) {
                boolean hasSlotDef = false;
                for (SlotDef slotDef : intentDef.getSlotDefs()) {
                    if (slotDef.getName().equalsIgnoreCase(slotName)) {
                        hasSlotDef = true;
                        break;
                    }
                }
                if (!hasSlotDef) {
                    SlotDef slotDef = new SlotDef();
                    slotDef.setName(slotName);
                    intentDef.getSlotDefs().add(slotDef);
                }
            }
        }
        Intent intent = new Intent(intentDef);
        intent.setProbability(element.getScore());
        intent.setSource(element.getSource());
        // filling slots
        if (element.getSlots() != null) {
            for (Map.Entry<String, List<SlotValue>> entry : element.getSlots().entrySet()) {
                Slot slot = intent.getSlotValueMap().get(entry.getKey());
                if (slot != null) {
                    slot.getSlotValues().addAll(entry.getValue());
                }
            }
        }
        return intent;
    }
}
