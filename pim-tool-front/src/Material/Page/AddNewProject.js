import React, {useEffect, useState} from "react";
import '../Style/AddNewProject.css'
import {Col, Container, Row} from "react-bootstrap";
import ContentHeader from "./ContentHeader";
import $ from "jquery";
import {useHistory} from "react-router-dom";
import Select from "react-select";
import axios from "axios";
import Translate from "react-translate-component";
import counterpart from "counterpart";
import en from '../lang/en';
import vi from '../lang/vi';

counterpart.registerTranslations('en', en);
counterpart.registerTranslations('vi', vi);

const AddNewProject = () => {
    const [ProjectNumber, setProjectNumber] = useState('');
    const [ProjectName, setProjectName] = useState('');
    const [Customer, setCustomer] = useState('');
    const [Group, setGroup] = useState([]);
    const [Status, setStatus] = useState('NEW');
    const [StartingDay, setStartingDay] = useState('');
    const [FinishingDay, setFinishingDay] = useState('');
    const [Member, setMember] = useState([]);

    const [Visa, setVisa] = useState([]);
    const statusCondition = [{key: "NEW", value: "NEW"}, {key: "Planned", value: "PLA"}, {
        key: "In Progress",
        value: "INP"
    }, {key: "Finished", value: "FIN"}]
    const [totalGroup, setTotalGroup] = useState([]);
    useEffect(() => {
        axios.get("http://localhost:8080/Groups/").then(response => {
            setTotalGroup(response.data);
        })
    }, [])


    const [listVisa, setListVisa] = useState([]);
    let listOfVisa = []
    useEffect(() => {
        axios.get("http://localhost:8080/Employees/")
            .then(response => {
                response.data.forEach(items => {
                    listOfVisa.push({value: items.visa, label: `${items.visa}: ${items.lastName} ${items.firstName}`})
                })
                setListVisa(listOfVisa);
            });
    }, [])

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
            if (key == 'project-number') {
                if (!new RegExp("^\\d{4}$").test(value[key])) {
                    errors[key] = `${enumName[key]} ${counterpart("invalid.projectNumberInvalid")}`;
                }
            }
            if (!value[key]) {
                errors[key] = `${enumName[key]}  ${counterpart("invalid.fieldInvalid")}`;
            }
        });

        // End date > Start date
        if (value["end-date"] != "") {
            if (value['start-date'] > value["end-date"]) {
                errors["end-date"] = counterpart("error.projectEndDate")
            }
        }

        Object.keys(errors).forEach(key => {
            if (errors[key]) {
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

    const SendData = (ProjectNumber, ProjectName, Customer, Group, Status, StartingDay, FinishingDay, Visa) => {
        const employees = Visa.map(value => {
            return {
                visa: value
            }
        })
        axios.post(
            'http://localhost:8080/projects/',
            {
                'projectNumber': ProjectNumber, 'name': ProjectName, 'customer': Customer, 'group': {id: Group},

                'status': Status, 'startingDate': StartingDay, 'finishingDate': FinishingDay, 'employee': employees
            }
        ).then(response => {
            history.push('/');
            window.location.reload();
        }).catch(error => {
            if (!error.response) {
                history.push('/error');
            } else {
                let message = "";
                const statusCode = error.response.data.status;
                if (statusCode == 500) {
                    const responseMess = error.response.data.message;
                    const code = responseMess.match(/^\w+(?=-)/i)[0];
                    if (code == "DUPLICATE_PROJECT_NUMBER") {
                        const projectNumber = responseMess.match(/\d+$/)[0];
                        let formatForm = counterpart("error.projectNumberDuplicated");
                        message = formatForm.replace(/#{4}/, projectNumber);
                        // message= counterpart("error.projectNumberDuplicated");
                    }
                    $(`span.error[name=project-number]`).text(message);
                    $(`span.error[name=project-number]`).addClass("error--show");
                    $(`input[name=project-number]`).addClass("error--show");
                }
            }
        });
    };

    const history = useHistory();
    const onSubmitClick = (event) => {
        event.preventDefault();
        if (validateForm()) {
            SendData(ProjectNumber, ProjectName, Customer, Group, Status, StartingDay, FinishingDay, Visa);
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
                            <input value={ProjectNumber} onChange={event => {
                                (
                                    setProjectNumber(event.target.value)
                                )
                            }} name="project-number"/>
                        </Col>
                        <Col xs={6}>
                        </Col>
                        <Col xs={3}/>
                        <Col xs={9}>
                            <span className="error" name="project-number"></span>
                        </Col>
                    </Row>
                    <Row className={"row--input"}>
                        <Col className={"titleLabel"} xs={3}>
                            <Translate content="addNewProject.projectName"/><i className={"requiredMark"}
                                                                               color={"red"}>*</i>
                        </Col>
                        <Col xs={9}>
                            <input value={ProjectName} onChange={event => {
                                setProjectName(event.target.value)
                            }} name="project-name"/>
                        </Col>
                        <Col xs={3}/>
                        <Col xs={9}>
                            <span className="error" name="project-name"></span>
                        </Col>
                    </Row>
                    <Row className={"row--input"}>
                        <Col className={"titleLabel"} xs={3}>
                            <Translate content="addNewProject.customer"/><i className={"requiredMark"}
                                                                            color={"red"}>*</i>
                        </Col>
                        <Col xs={9}>
                            <input value={Customer} onChange={event => {
                                setCustomer(event.target.value)
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
                            <select value={Group} onChange={event => setGroup(event.target.value)}
                                    className="ui dropdown"
                                    name="groups">
                                <option value="" selected disabled>--{counterpart("field.selectText")}--</option>
                                {totalGroup.map(item => {
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
                                defaultValue={[]}
                                isMulti
                                name="visa"
                                onMenuOpen={listVisa}
                                options={listVisa}
                                className="basic-multi-select"
                                classNamePrefix="select"
                                placeholder={counterpart("field.selectText")}
                                onChange={(event) => {
                                    if (event) {
                                        let arr = [];
                                        event.forEach((items) => {
                                            arr.push(items.value);
                                            setVisa(arr);
                                        })
                                    } else setVisa([])

                                }}

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
                            <select value={Status} onChange={event => {
                                setStatus(event.target.value)
                            }} className="ui dropdown" name="status">
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
                                    <input type={"date"} value={StartingDay} onChange={event => {
                                        setStartingDay(event.target.value)
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
                                    <input type={"date"} value={FinishingDay} onChange={event => {
                                        setFinishingDay(event.target.value)
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
                        <Translate content="field.createButton"/>
                    </button>
                </div>
            </form>


        </Container>
    );
}
export default AddNewProject;