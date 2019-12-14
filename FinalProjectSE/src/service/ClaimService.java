package service;

import model.Claim;
import model.Contract;
import model.Obligation;
import model.Person;

import service.common.MainService;
import service.exception.ValidationException;
import view.common.Menu;

import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ClaimService extends MainService<Byte> {
    private Claim claimModel;
    private ContractService contractService = new ContractService();
    private static Long id = 0L;

    public ClaimService() {
    }

    @Override
    public ClaimService setModel(model.common.Common claimModel) {
        this.claimModel = claimModel == null ? new Claim() : (Claim) claimModel;
        return this;
    }

    @Override
    public ArrayList<Claim> getRelevantArrayList() {
        return claimArrayList;
    }

    public Claim getInstance() {
        return claimModel;
    }

    public void add() {
        claimModel = new Claim();
        setId(++id);
        setClaimDate();
        setContractId();
        setInsuredId();
        setObligationId();
        setAmount();
        calculateAndFillOtherFields();
        claimArrayList.add(claimModel);
    }

    public ArrayList searchByNationalCode(Optional<Person> person) throws ValidationException {
        if (person.isEmpty())
            throw new ValidationException("national code is not in person list");
        return (ArrayList) claimArrayList.stream().filter(item -> item.getInsuredId().equals(person.get().getId()))
                .collect(Collectors.toList());
    }

    private void calculateAndFillOtherFields() {
        Supplier<Stream<Claim>> claimSupplier = () -> claimArrayList.stream()
                .filter(item ->
                        item.getContractId().equals(claimModel.getContractId()) &&
                                item.getInsuredId().equals(claimModel.getInsuredId()) &&
                                item.getObligationId().equals(claimModel.getObligationId())
                );
        //ta konon
        Long formerInsureAmount = claimSupplier.get().map(Claim::getInsureAmount).reduce(Long::sum).orElse(0L);
        Long overCeiling = claimSupplier.get().map(Claim::getOverCeiling).max(Long::compareTo).orElse(0L);
        Long amount = claimModel.getAmount();
        if (overCeiling.equals(0L)) {
            ArrayList<Obligation> obligationArrayList =
                    (ArrayList<Obligation>) contractService.getInstance().getObligationList().stream()
                            .filter(item -> item.getId().equals(claimModel.getObligationId()))
                            .collect(Collectors.toList());

            Supplier<Stream<Obligation>> supplier = () -> obligationArrayList.stream();
            Optional<Byte> fronchise = supplier.get().map(item -> item.getFronchise()).findFirst();
            Long ceiling = supplier.get().map(item -> item.getCeiling()).findFirst().orElse(0L);

            Long temp1 = ((fronchise.orElse((byte) 0) * amount) / 100);
            Long temp2 = amount - temp1;
            Long temp3 = temp2 + formerInsureAmount;

            if (temp3 > ceiling) {
                setOverCeiling(temp3 - ceiling);
                setInsureAmount(ceiling - formerInsureAmount);
            } else {
                setOverCeiling(0L);
                setInsureAmount(temp2);
            }
        } else {
            setOverCeiling(overCeiling + amount);
            setInsureAmount(0L);
        }

        setInsurePersonAmount(amount - claimModel.getInsureAmount());
    }

    public void setId(Long id) {
        claimModel.setId(id);
    }

    public void setContractId() {
        String claimDate = claimModel.getClaimDate();
        Contract temp =
                getSelectedList(null,
                        // filter contracts that this date is valid for them
                        (ArrayList) contractArrayList.stream()
                                .filter(item -> claimDate.compareTo(item.getStartDate()) >= 0 &&
                                        claimDate.compareTo(item.getEndDate()) <= 0)
                                .collect(Collectors.toList())
                        ,
                        new ContractService(),
                        Menu.NumberType.LONG, false, Contract.class.getSimpleName());
        contractService.setModel(temp);
        claimModel.setContractId(temp.getId());
    }

    public void setInsuredId() {
        Person temp =
                getSelectedList(null,
                        contractService.getInstance().getInsurePersonList(),
                        new PersonService(),
                        Menu.NumberType.LONG, false, Person.class.getSimpleName());
        claimModel.setInsuredId((Long) temp.getId());
    }

    public void setObligationId() {
        Obligation temp =
                getSelectedList(null,
                        contractService.getInstance().getObligationList(),
                        new ObligationService(),
                        Menu.NumberType.LONG, false, Obligation.class.getSimpleName());
        claimModel.setObligationId(temp.getId());
    }

    public void setClaimDate() {
        extraInfoForValidationMethod.put(ExtraInfoKeys.MSG, "claim date ");
        String claimDate = menu.getValidField(Menu.ControlType.DATE, extraInfoForValidationMethod);
        claimModel.setClaimDate(claimDate);
    }

    public void setAmount() {
        extraInfoForValidationMethod.put(ExtraInfoKeys.MSG, "amount");
        Long amount = menu.getValidField(Menu.ControlType.NUMBER, extraInfoForValidationMethod);
        claimModel.setAmount(amount);
    }

    public void setInsureAmount(Long insureAmount) {
        claimModel.setInsureAmount(insureAmount);
    }

    public void setInsurePersonAmount(Long insurePersonAmount) {
        claimModel.setInsurePersonAmount(insurePersonAmount);
    }

    public void setOverCeiling(Long overCeiling) {
        claimModel.setOverCeiling(overCeiling);
    }

    @Override
    public String toString() {
        return " id=" + claimModel.getId() +
                ", insuredName=" + ((Person) findObjectById(personArrayList, claimModel.getInsuredId())).getName() +
                ", contractId=" + claimModel.getContractId() +
                ", obligationName=" + ((Obligation) findObjectById(obligationArrayList, claimModel.getObligationId())).getName() +
                ", claimDate=" + claimModel.getClaimDate() +
                ", amount=" + showMoneyFormat(claimModel.getAmount()) +
                ", insureAmount=" + showMoneyFormat(claimModel.getInsureAmount()) +
                ", insurePersonAmount=" + showMoneyFormat(claimModel.getInsurePersonAmount()) +
                ", overCeiling=" + showMoneyFormat(claimModel.getOverCeiling());
    }
}
