package pocs.optaplanner.delivery.domain;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.optaplanner.core.api.score.buildin.hardmediumsoft.HardMediumSoftScore;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pocs.optaplanner.delivery.domain.aircrew.Aircrew;
import pocs.optaplanner.delivery.domain.aircrew.AircrewAvailability;
import pocs.optaplanner.delivery.domain.common.AbstractPersistable;
import pocs.optaplanner.delivery.domain.skills.Skill;

@Data
@EqualsAndHashCode(callSuper = true)
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class DeliverySchedule extends AbstractPersistable {

	private List<Skill> skillList = new ArrayList<>();
	private List<DeliveryRole> deliveryRoleList = new ArrayList<>();
	private List<Aircrew> airCrewList = new ArrayList<>();
	private List<AircrewAvailability> aircrewAvailabilityList = new ArrayList<>();
	private List<DeliveryAssignment> deliveryAssignmentList = new ArrayList<>();
	private HardMediumSoftScore score = null;

}
