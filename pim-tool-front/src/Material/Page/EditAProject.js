import React, {useEffect, useState} from "react";
import {Col, Container, Row} from "react-bootstrap";
import ContentHeader from "./ContentHeader";
import axios from "axios";
import $ from "jquery";
import {useHistory, useLocation} from "react-router-dom";
import Select from "react-select";
import counterpart from "counterpart";
import Translate from "react-translate-component";
import vi from "../lang/vi";


const EditAProject = () => {
    const [ProjectNumberEdit, setProjectNumberEdit] = useState('');
    const [ProjectNameEdit, setProjectNameEdit] = useState('');
    const [CustomerEdit, setCustomerEdit] = useState('');
    const [GroupEdit, setGroupEdit] = useState('');
    const [StatusEdit, setStatusEdit] = useState('');
    const [StartingDayEdit, setStartingDayEdit] = useState('');
    const [FinishingDayEdit, setFinishingDayEdit] = useState('');
    const [Version, SetVersion] = useState();
    const location = useLocation();

    const statusCondition = [{key: "NEW", value: "NEW"}, {key: "Planned", value: "PLA"}, {
        key: "In Progress",
        value: "INP"
    }, {key: "Finished", value: "FIN"}]
    const [VisaEdit, setVisaEdit] = useState([]);

    const [totalGroupEdit, setTotalGroupEdit] = useState([]);
    useEffect(() => {
        axios.get("http://localhost:8080/Groups/").then(response => {
            setTotalGroupEdit(response.data);
        })
    }, [])

    const [listVisaEdit, setListVisaEdit] = useState([]);
    let listOfVisa = []
    useEffect(() => {
        axios.get("http://localhost:8080/Employees/")
            .then(response => {
                response.data.forEach(items => {
                    listOfVisa.push({value: items.visa, label: `${items.visa}: ${items.lastName} ${items.firstName}`})
                })
                setListVisaEdit(listOfVisa);
            });
    }, [])

    const findDefaultValueVisa = () => {
        let result = [];
        VisaEdit.forEach(item => {
            let itemVisa;
            if (!item.visa) {
                itemVisa = item.value;
            } else {
                itemVisa = item.visa;
            }
            const visa = listVisaEdit.filter((item) => {
                return item.value == itemVisa;
            })
            result.push(visa[0]);
        })
        return result;
    }
    const defaultValues = findDefaultValueVisa();
    console.log(defaultValues);
    const params = new URLSearchParams(location.search);
    const projectNumber = params.get('projectnumber');

    useEffect(() => {
        axios.get(
            `http://localhost:8080/projects/${projectNumber}`,
        ).then(response => (
            response.data.forEach((item => {
                setProjectNumberEdit(item.projectNumber);
                setProjectNameEdit(item.name);
                setCustomerEdit(item.customer);
                setGroupEdit(item.group)
                setStatusEdit(item.status);
                setStartingDayEdit(item.startingDate);
                setFinishingDayEdit(item.finishingDate);
                setVisaEdit(item.employee);
                SetVersion(item.version);
            }))
        ));

    }, []);
    const updateProject = (ProjectNumberEdit, ProjectNameEdit, CustomerEdit, StatusEdit, StartingDayEdit, FinishingDayEdit, GroupEdit, VisaEdit) => {

        const employees = VisaEdit.map(value => {
            return {
                visa: value
            }
        })
        axios.put(
            'http://localhost:8080/projects/update/',
            {
                'projectNumber': ProjectNumberEdit,
                'name': ProjectNameEdit,
                'customer': CustomerEdit,
                'status': StatusEdit,
                'startingDate': StartingDayEdit,
                'finishingDate': FinishingDayEdit,
                'group': {id: GroupEdit.id},
                'employee': employees,
                'version': Version
            }
        ).then(response => {
            console.log(response)
            history.push('/');
            window.location.reload();
        }).catch(error => {
            // server is disabled
            if (!error.response) {
                history.push('/error');
            } else {
                let message = "";
                const statusCode = error.response.data.status;
                if (statusCode == 500) {
                    const responseMess = error.response.data.message;
                    if (!responseMess.includes("CONCURRENT_UPDATE")) {
                        history.push('/error');
                    } else {
                        const code = responseMess.match(/^\w+(?=-)/i)[0];
                        if (code == "CONCURRENT_UPDATE") {
                            const projectNumber = responseMess.match(/\d+$/)[0];
                            let formatForm = counterpart("error.projectNumberConcurrentUpdate");
                            message = formatForm.replace(/#{4}/, projectNumber);
                        }
                    }
                    $(`span.alert-validate`).text(message);
                    $(`span.alert-validate`).removeClass("d-none");
                    // const message = counterpart("error.concurrentUpdate");
                    // $(`span.alert-validate`).text(message);
                    // $(`span.alert-validate`).removeClass("d-none");
                }
            }
        });
    }

    const handleVisaEdit = () => {
        const result = VisaEdit.reduce((current, item, index) => {
            let temp = item.visa ?? item.value;
            current.push(temp);
            return current;
        }, [])
        return result;
    }

    const validateForm = () => {
        let enumName = {
            "project-name": counterpart("field.project_name"),
            "project-number": counterpart("field.project_number"),
            "customer": counterpart("field.customer"),
            "groups": counterpart("field.groups"),
            "member": counterpart("field.member"),
            "status": counterpart("field.status"),
            "start-date": counterpart("field.start_date"),
            "end-date": counterpart("field.end_date"),
        };
        let value = {};
        value['project-name'] = document.querySelector("input[name='project-name']").value;
        value['project-number'] = document.querySelector("input[name='project-number']").value;
        value.customer = document.querySelector("input[name='customer']").value;
        value.groups = document.querySelector("select[name='groups']").value;
        //   value.member = document.querySelector("input[name='member']").value;
        value.status = document.querySelector("select[name='status']").value;
        value['start-date'] = document.querySelector("input[name='start-date']").value;
        value['end-date'] = document.querySelector("input[name='end-date']").value;
        let errors = {};

        $(".error--show").removeClass("error--show");
        $(".alert-validate").addClass("d-none");

        // Code Empty Validate
        Object.keys(value).forEach(key => {
            if (key == "end-date") {
                return;
            }
            if (!value[key]) {
                errors[key] = `${enumName[key]}  ${counterpart("invalid.fieldInvalid")}`;
            }
        });

        if (value["end-date"] != "") {
            if (value['start-date'] > value["end-date"]) {
                errors["end-date"] = counterpart("error.projectEndDate")
            }
        }

        Object.keys(errors).forEach(key => {
            if (errors[key]) {
                console.log(errors[key]);
                $(`span.error[name=${key}]`).text(errors[key]);
                $(`span.error[name=${key}]`).addClass("error--show");
                $(`input[name=${key}]`).addClass("error--show");
                $(".alert-validate").removeClass("d-none");
            }
        });

        if (Object.keys(errors).length == 0) {
            return true;
        }
        return false;
    }

    const history = useHistory();
    const onSubmitClick = (event) => {
        event.preventDefault();
        if (validateForm()) {
            const VisaArr = handleVisaEdit();
            updateProject(ProjectNumberEdit, ProjectNameEdit, CustomerEdit, StatusEdit, StartingDayEdit, FinishingDayEdit, GroupEdit, VisaArr);
        }
    }
    const onSubmitCancelClick = () => {
        history.push('/');
    }
    const handleStatusLang = (status) => {
        if (status == "NEW") {
            return counterpart("statusHandle.NEW")
        }
        if (status == "INP") {
            return counterpart("statusHandle.INP")
        }
        if (status == "FIN") {
            return counterpart("statusHandle.FIN")
        }
        if (status == "PLA") {
            return counterpart("statusHandle.PLA")
        }
    }
    return (
        <Container>
            <ContentHeader/>
            <form id={"formCreateNewProject"} className={"ui fluid form"}>
                <div className={'field'}>
                    <Row className={"pl-3"}>
                        <span
                            className={"w-100 alert alert-danger alert-validate d-none"}>{counterpart("error.mandatoryField")}</span>
                    </Row>
                    <Row className={"row--input"}>
                        <Col className={"titleLabel"} xs={3}>
                            <Translate content="addNewProject.projectNumber"/><i className={"requiredMark"}
                                                                                 color={"red"}>*</i>
                        </Col>
                        <Col xs={3}>
                            <input disabled value={projectNumber} onChange={event => {
                                setProjectNumberEdit(event.target.value)
                            }} name="project-number"/>
                        </Col>
                        <Col xs={6}>
                            {/*<input type="text"/>*/}
                        </Col>
                        <Col xs={3}/>
                        <Col xs={9}>
                            <span className="error error--show" name="project-number"></span>
                        </Col>
                    </Row>
                    <Row className={"row--input"}>
                        <Col className={"titleLabel"} xs={3}>
                            <Translate content="addNewProject.projectName"/><i className={"requiredMark"}
                                                                               color={"red"}>*</i>
                        </Col>
                        <Col xs={9}>
                            <input value={ProjectNameEdit} onChange={event => {
                                setProjectNameEdit(event.target.value)
                            }} name="project-name"/>
                        </Col>
                        <Col xs={3}/>
                        <Col xs={9}>
                            <span className="error error--show" name="project-name"></span>
                        </Col>
                    </Row>
                    <Row className={"row--input"}>
                        <Col className={"titleLabel"} xs={3}>
                            <Translate content="addNewProject.customer"/><i className={"requiredMark"}
                                                                            color={"red"}>*</i>
                        </Col>
                        <Col xs={9}>
                            <input value={CustomerEdit} onChange={event => {
                                setCustomerEdit(event.target.value)
                            }} name="customer"/>
                        </Col>
                        <Col xs={3}/>
                        <Col xs={9}>
                            <span className="error error--show" name="customer"></span>
                        </Col>
                    </Row>
                    <Row className={"row--input"}>
                        <Col className={"titleLabel"} xs={3}>
                            <Translate content="addNewProject.group"/><i className={"requiredMark"} color={"red"}>*</i>
                        </Col>
                        <Col xs={3}>
                            <select value={GroupEdit.id} onChange={event => {
                                setGroupEdit({id: event.target.value});
                            }} className="ui dropdown" name="groups">
                                <option selected disabled value="">--{counterpart("field.selectText")}--</option>
                                {totalGroupEdit.map(item => {
                                    return <option value={item.id}> {item.groupLeaderId.visa}</option>
                                })}
                            </select>
                        </Col>
                        <Col xs={6}>
                        </Col>
                        <Col xs={3}/>
                        <Col xs={9}>
                            <span className="error error--show mt-2" name="groups"></span>
                        </Col>
                    </Row>
                    <Row className={"row--input"}>
                        <Col className={"titleLabel"} xs={3}>
                            <Translate content="addNewProject.member"/>
                        </Col>
                        <Col xs={9}>
                            <Select
                                value={defaultValues}
                                isMulti
                                name="visa"
                                onMenuOpen={listVisaEdit}
                                options={listVisaEdit}
                                className="basic-multi-select"
                                classNamePrefix="select"
                                placeholder={counterpart("field.selectText")}
                                onChange={(event) => {
                                    if (event) {
                                        setVisaEdit(event);
                                    } else {
                                        setVisaEdit([]);
                                    }
                                }
                                }
                            />
                        </Col>
                        <Col xs={3}/>
                        <Col xs={9}>
                            <span className="error error--show" name="member"></span>
                        </Col>
                    </Row>
                    <Row className={"row--input"}>
                        <Col className={"titleLabel"} xs={3}>
                            <Translate content="addNewProject.status"/><i className={"requiredMark"} color={"red"}>*</i>
                        </Col>
                        <Col xs={3}>
                            <select value={StatusEdit} onChange={event => setStatusEdit(event.target.value)}
                                    className="ui dropdown"
                                    name="status">
                                <option selected disabled value="">--Select--</option>
                                {statusCondition.map((items) => {
                                    return <option value={items.value}> {handleStatusLang(items.value)}</option>
                                })}
                            </select>
                        </Col>
                        <Col xs={6}>
                        </Col>
                        <Col xs={3}/>
                        <Col xs={9}>
                            <span className="error error--show mt-2" name="status"></span>
                        </Col>
                    </Row>
                    <Row className={"row--input"}>
                        <Col xs={6}>
                            <Row>
                                <Col className={"titleLabel"} xs={6}>
                                    <Translate content="addNewProject.startDate"/><i className={"requiredMark"}
                                                                                     color={"red"}>*</i>
                                </Col>
                                <Col xs={6}>
                                    <input type={"date"} value={StartingDayEdit} onChange={event => {
                                        setStartingDayEdit(event.target.value)
                                    }} name="start-date"/>

                                </Col>
                            </Row>
                        </Col>

                        <Col xs={6}>
                            <Row>
                                <Col className={["titleLabel", "titleLabelCol2"]} xs={6}>
                                    <Translate content="addNewProject.endDate"/>
                                </Col>
                                <Col xs={6}>
                                    <input type={"date"} value={FinishingDayEdit} onChange={event => {
                                        setFinishingDayEdit(event.target.value)
                                    }}
                                           name="end-date"/>
                                </Col>
                            </Row>
                        </Col>
                        <Col xs={3}/>
                        <Col xs={3} className={"mt-3"}>
                            <span className="error error--show" name="start-date"></span>
                        </Col>
                        <Col xs={3}/>
                        <Col xs={3} className={"mt-3"}>
                            <span className="error error--show" name="end-date"></span>
                        </Col>
                    </Row>
                </div>
                <div className={"buttonDirection"}>
                    <button onClick={onSubmitCancelClick} id={"cancelButton"} className="ui button">
                        <Translate content="field.cancelButton"/>
                    </button>
                    <button onClick={onSubmitClick} id={"createNewProjectButton"} className="ui primary button">
                        <Translate content="field.saveButton"/>
                    </button>
                </div>
            </form>
        </Container>
    );
}
export default EditAProject;