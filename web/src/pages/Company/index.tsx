import React, { useEffect, useState } from "react";

import Modal from "../../components/Modal";
import InsertDataModal from "../../components/Modal/InsertDataModal";
import TableCRUD from "../../components/TableCRUD";
import TopFindAndCreateMenu from "../../components/TopFindAndCreateMenu";

import styles from "./style.module.css";
import HeaderModal from "../../components/Modal/HeaderModal";
import FooterModal from "../../components/Modal/FooterModal";
import BodyModal from "../../components/Modal/BodyModal";
import Input, { ErrorType } from "../../components/Input";
import { CompanyModel, CompanyService } from "../../services/CompanyService";
import Loading from "../../components/Loader/Loading";

enum ModalType {
    None,
    Insert,
    Update,
    Preview,
    Delete,
}

export default function Company() {
    const [modalContent, setModalContent] = useState(ModalType.None);
    const [openModal, setOpenModal] = useState(false);
    const [openLoad, setOpenLoad] = useState(true);

    const companyNone = {
        name: "",
        description: "",
        id: 0,
    };

    const [companyModel, setCompanyModel] = useState<CompanyModel>(companyNone);

    const [containsErrors, setContainsErrors] = useState(false);
    const [validErrors, setValidErrors] = useState(false);

    const [companies, setCompanies] = useState<CompanyModel[]>([]);

    const [selectCompany, setSelectCompany] =
        useState<CompanyModel>(companyNone);

    const companyService = new CompanyService();

    useEffect(() => {
        companyService.findAll().then(setCompanies);
    }, []);

    useEffect(() => setOpenLoad(false), [companies]);

    function saveCompany(): void {
        setValidErrors(true);

        if (containsErrors) {
            setValidErrors(!validErrors);
            return;
        }

        setOpenLoad(true);
        companyService
            .save(companyModel)
            .then((company) => {
                setCompanies([...companies, company]);
                setCompanyModel(companyNone);
                setOpenModal(false);
                setValidErrors(false);
            })
            .catch((error) => {
                setOpenLoad(false);
            });
    }

    function updateCompany(): void {
        setValidErrors(true);

        if (containsErrors) {
            setValidErrors(!validErrors);
            return;
        }

        setOpenLoad(true);
        companyService
            .update(selectCompany)
            .then((company) => {
                setCompanies([
                    ...companies.filter((c) => c.id !== company.id),
                    company,
                ]);
                setCompanyModel(companyNone);
                setOpenModal(false);
                setValidErrors(false);
            })
            .catch((error) => {
                //TODO show error message
                setOpenLoad(false);
            });
    }

    function deleteCompany(): void {
        setOpenLoad(true);

        companyService
            .deleteById(selectCompany.id)
            .then((success) => {
                setCompanies(
                    companies.filter((comp) => comp.id !== selectCompany.id)
                );
            })
            .finally(() => {
                setOpenModal(false);
            });
    }

    function openModalUpdate() {
        return (
            <InsertDataModal useHBF={true}>
                <HeaderModal
                    title="Company"
                    subtitle="Update company"
                    closeButton={true}
                    onClickCloseButton={() => setOpenModal(false)}
                />
                <BodyModal>
                    <Input
                        id="inputNameInsertUpdateCompany"
                        type="text"
                        labelName="Name"
                        stylesName={[]}
                        name="CompanyName"
                        value={selectCompany.name}
                        validErrors={validErrors}
                        setContainsErros={setContainsErrors}
                        onChange={(ev) =>
                            setSelectCompany({
                                ...selectCompany,
                                name: ev.target.value,
                            })
                        }
                        errors={[
                            {
                                error: ErrorType.EMPTY,
                                message: "The name don't hold to be empty.",
                            },
                            {
                                error: ErrorType.MIN,
                                message:
                                    "The name should have minimum(2) characters",
                                propert: "2",
                            },
                        ]}
                    />
                    <Input
                        id="inputDescriptionInsertUpdateCompany"
                        type="text"
                        labelName="Description"
                        stylesName={[]}
                        name="CompanyDescription"
                        value={selectCompany.description}
                        validErrors={validErrors}
                        setContainsErros={setContainsErrors}
                        onChange={(ev) =>
                            setSelectCompany({
                                ...selectCompany,
                                description: ev.target.value,
                            })
                        }
                        errors={[
                            {
                                error: ErrorType.MIN,
                                message:
                                    "The name should have minimum(2) characters",
                                propert: "2",
                            },
                        ]}
                    />
                </BodyModal>
                <FooterModal
                    buttons={[
                        {
                            name: "Cancel",
                            color: "var(--color-button)",
                            onAction: () => setOpenModal(false),
                            outline: true,
                        },
                        {
                            name: "Create",
                            color: "var(--color-button)",
                            onAction: updateCompany,
                        },
                    ]}
                />
            </InsertDataModal>
        );
    }

    function openModalDelete() {
        return (
            <InsertDataModal useHBF={true}>
                <HeaderModal title="Confirm delete" />
                <BodyModal>
                    <div>
                        <h4>Do you really want to remove this company ?</h4>
                        <br />
                        <h4>
                            id: {selectCompany.id}
                            <br />
                            name: {selectCompany.name}
                        </h4>
                    </div>
                </BodyModal>
                <FooterModal
                    buttons={[
                        {
                            name: "Cancel",
                            color: "var(--color-button)",
                            onAction: () => setOpenModal(false),
                            outline: true,
                        },
                        {
                            name: "Confirm",
                            color: "var(--color-button)",
                            onAction: deleteCompany,
                        },
                    ]}
                />
            </InsertDataModal>
        );
    }

    function openModalInsert() {
        return (
            <InsertDataModal useHBF={true}>
                <HeaderModal
                    title="Company"
                    subtitle="Inserting new company"
                    closeButton={true}
                    onClickCloseButton={() => setOpenModal(false)}
                />
                <BodyModal>
                    <Input
                        id="inputNameInsertUpdateCompany"
                        type="text"
                        labelName="Name"
                        name="CompanyName"
                        stylesName={[]}
                        value={companyModel.name}
                        validErrors={validErrors}
                        setContainsErros={setContainsErrors}
                        onChange={(ev) =>
                            setCompanyModel({
                                ...companyModel,
                                name: ev.target.value,
                            })
                        }
                        errors={[
                            {
                                error: ErrorType.EMPTY,
                                message: "The name don't hold to be empty.",
                            },
                            {
                                error: ErrorType.MIN,
                                message:
                                    "The name should have minimum(2) characters",
                                propert: "2",
                            },
                        ]}
                    />
                    <Input
                        id="inputDescriptionInsertUpdateCompany"
                        type="text"
                        labelName="Description"
                        stylesName={[]}
                        name="CompanyDescription"
                        value={companyModel.description}
                        validErrors={validErrors}
                        setContainsErros={setContainsErrors}
                        onChange={(ev) =>
                            setCompanyModel({
                                ...companyModel,
                                description: ev.target.value,
                            })
                        }
                        errors={[
                            {
                                error: ErrorType.MIN,
                                message:
                                    "The name should have minimum(2) characters",
                                propert: "2",
                            },
                        ]}
                    />
                </BodyModal>
                <FooterModal
                    buttons={[
                        {
                            name: "Cancel",
                            color: "var(--color-button)",
                            onAction: () => setOpenModal(false),
                            outline: true,
                        },
                        {
                            name: "Create",
                            color: "var(--color-button)",
                            onAction: saveCompany,
                        },
                    ]}
                />
            </InsertDataModal>
        );
    }

    function getModalContent() {
        switch (modalContent) {
            case ModalType.Insert:
                return openModalInsert();
            case ModalType.Update:
                return openModalUpdate();
            case ModalType.Delete:
                return openModalDelete();
            default:
                return <span></span>;
        }
    }

    return (
        <div className="container">
            {openModal ? (
                <Modal width="78%" height="58%">
                    {getModalContent()}
                </Modal>
            ) : (
                <span></span>
            )}

            <TopFindAndCreateMenu
                buttonCreateName="Insert new company"
                onClickInsert={() => {
                    setModalContent(ModalType.Insert);
                    setOpenModal(true);
                }}
            />

            <div id={styles.subContainer}>
                <h1 className="titleCrud">Companies</h1>

                <div id={styles.fixFiltersContainer}></div>

                <TableCRUD
                    value={companies}
                    fields={["Id", "Name", "Description"]}
                    actions={{ update: true, find: false, delete: true }}
                    onUpdateAction={(content) => {
                        setModalContent(ModalType.Update);
                        setOpenModal(true);
                        setSelectCompany(content as CompanyModel);
                    }}
                    onDeleteAction={(content) => {
                        setModalContent(ModalType.Delete);
                        setOpenModal(true);
                        setSelectCompany(content as CompanyModel);
                    }}
                />
            </div>
            {openLoad && <Loading />}
        </div>
    );
}
