import React, {useEffect, useState} from "react";
import {Col, Container, Row} from "react-bootstrap";
import '../Style/ProjectList.css'
import ContentHeader from "./ContentHeader";
import {BsFillTrashFill} from "react-icons/bs";
import axios from "axios";
import Pagination from "react-js-pagination";
import en from '../lang/en';
import vi from '../lang/vi';
import Translate from 'react-translate-component';
import counterpart from "counterpart";
import {useHistory} from "react-router-dom";

counterpart.registerTranslations('en', en);
counterpart.registerTranslations('vi', vi);


const ProjectList = () => {


    const [ValueCondition, setValueCondition] = useState('');
    const [ValueStatus, setValueStatus] = useState('')
    const [data, setData] = useState([]);
    const [defaultData, setDefaultData] = useState([]); // full mảng project lấy từ DB
    const [countItem, setCountItem] = useState(0);  // Tổng số lượng item
    const [itemPerPage, setItemPerPage] = useState(5); // Số item mỗi trang
    const [indexPage, setIndexPage] = useState(1); // số trang hiện tại
    const statusCondition = [{key: "NEW", value: "NEW"}
        , {key: "Planned", value: "PLA"}
        , {key: "In Progress", value: "INP"}
        , {key: "Finished", value: "FIN"}]

    // [1, 2, 3 , 4, 5, 6, 7, 8, 9, 10, 11, 12]
    const handlePageChange = (pageNumber) => {
        let i = (pageNumber - 1) * itemPerPage;
        const end = i + itemPerPage;
        let arr = defaultData.slice(i, end);
        setIndexPage(pageNumber);
        setData(arr);
    }

    const fetchData = async () => {
        const result = await axios(
            'http://localhost:8080/projects/',
        ).then(response => {
            response.data.forEach(item => item.select = false);

            const beginIndex = (indexPage - 1) * itemPerPage;
            const endIndex = beginIndex + itemPerPage;
            setData(response.data.slice(beginIndex, endIndex));

            setDefaultData(response.data);
            setCountItem(response.data.length);
        }).catch(error => {
            history.push('/error');
        })
    };

    const history = useHistory();
    useEffect(() => {
        fetchData();
    }, []);

    const checkSelected = () => {
        const elements = document.querySelectorAll("table input[type=checkbox]:checked");
        const countSelected = elements.length;
        if (countSelected <= 0) {
            return "";
        } else {
            return <tr>
                <td colspan='7'>
                    <p id="notify" className={"text-primary"}>
                        {countSelected} {counterpart("itemSelect.selected")}
                        <a href="/#" onClick={OnDeleteClick}
                           className={"text-danger"}>{counterpart("itemSelect.deletedSelected")}
                            <BsFillTrashFill color={"red"}/></a>
                    </p>
                </td>
            </tr>
        }
    }

    const [StateProjectNumber, setStateProjectNumber] = useState([]);
    const onSubmitClick = (e) => {
        e.preventDefault();
        const fetchData = async () => {
            const result = await axios(
                `http://localhost:8080/projects/search/?StringCondition=${ValueCondition}&StringStatus=${ValueStatus}`,
            ).then((response) => {
                const beginIndex = 0;
                const endIndex = beginIndex + itemPerPage;
                setData(response.data.slice(beginIndex, endIndex));
                setIndexPage(1);
                setDefaultData(response.data);
                setCountItem(response.data.length);
            });
        };
        fetchData();
    }
    const onSubmitClickSearch = (e) => {
        e.preventDefault();
        setValueStatus("");
        setValueCondition("")
        const fetchData = async () => {
            const result = await axios(
                'http://localhost:8080/projects/',
            ).then(response => {
                // setData(response.data)
                response.data.forEach(item => item.select = false);
                const beginIndex = (indexPage - 1) * itemPerPage;
                const endIndex = beginIndex + itemPerPage;
                setData(response.data.slice(beginIndex, endIndex));

                setDefaultData(response.data);
                setCountItem(response.data.length);
            })
        };
        fetchData();
    }

    let arrayProjectNumber = [];
    const OnDeleteClick = () => {
        if (window.confirm(counterpart("alertConfirm.messageAlert"))) {
            arrayProjectNumber = StateProjectNumber.filter(project => project.select && project.status === "NEW").map(project => project.projectNumber);
            const projectList = arrayProjectNumber.map(value => {
                return {
                    projectNumber: value
                }
            })
            console.log(JSON.stringify({'projectNumber': projectList}));
            axios.post(`http://localhost:8080/projects/delete/`,
                arrayProjectNumber).then(response => console.log(response));
            window.location.reload();
        }

    };
    const onRemoveClick = (projectNumber) => {
        if (window.confirm(counterpart("alertConfirm.messageAlert"))) {
            axios.delete(
                `http://localhost:8080/projects/delete/${projectNumber}`
            ).then(() => {
                fetchData();
            }).catch((error) => {
                fetchData();
            });
        }

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
            <form id={"formProjectList"} className={"ui fluid form"} placeholder={counterpart("field.searchInputText")}>
                <div className={'field'}>
                    <Row>
                        <Col xs={4}>
                            <input value={ValueCondition} type={"text"}
                                   placeholder={counterpart("field.searchInputText")}
                                   onChange={event => setValueCondition(event.target.value)}/>
                        </Col>
                        <Col xs={2}>
                            <select value={ValueStatus} onChange={event => setValueStatus(event.target.value)}
                                    placeholder={counterpart("field.searchStatusSelection")} className="ui dropdown">
                                <option defaultChecked>{counterpart("field.searchStatusSelection")}</option>
                                {statusCondition.map((items) => {
                                    return <option value={items.value}>{handleStatusLang(items.value)}</option>
                                })}
                            </select>
                        </Col>
                        <Col xs={3}>
                            <button onClick={onSubmitClick} className={"ui primary button"}><Translate
                                content={"field.searchButton"}/></button>
                        </Col>
                        <Col xs={3}>
                            <a onClick={onSubmitClickSearch}><Translate content={"field.resetSearch"}/></a>
                        </Col>
                    </Row>
                </div>
                <span id={"alert"} className={"w-100 alert alert-danger error"}>CC</span>
                <table id={"project-table"} className="table table-bordered">
                    <thead>
                    <tr>
                        <th style={{textAlign: "center"}}>#</th>
                        <th><Translate content="field.project_number"/></th>
                        <th><Translate content="field.project_name"/></th>
                        <th><Translate content="field.status"/></th>
                        <th><Translate content="field.customer"/></th>
                        <th><Translate content="field.start_date"/></th>
                        <th><Translate content="field.deleteIcon"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    {data.map(item => (
                        <tr>
                            <td data-label="">
                                {item.status === "NEW" ? <input type="checkbox" onChange={e => {
                                    let value = e.target.checked;
                                    setStateProjectNumber(data.map(d => {
                                        if (item.projectNumber == d.projectNumber) {
                                            d.select = value;
                                        }
                                        return d;
                                    }))
                                }}/> : <input type="checkbox" disabled={true}/>}
                                {/*<input type="checkbox" onChange={e => {*/}
                                {/*    let value = e.target.checked;*/}
                                {/*    setStateProjectNumber(data.map(d => {*/}
                                {/*        if (item.projectNumber == d.projectNumber) {*/}
                                {/*            d.select = value;*/}
                                {/*        }*/}
                                {/*        return d;*/}
                                {/*    }))*/}
                                {/*}}/>*/}
                            </td>
                            <td data-label="Number"><a
                                href={`/edit?projectnumber=${item.projectNumber}`}>{item.projectNumber}</a></td>
                            <td data-label="Name">{item.name}</td>
                            <td data-label="Status">{handleStatusLang(item.status)}</td>
                            <td data-label="Customer">{item.customer}</td>
                            <td data-label="Start Date">{item.startingDate}</td>
                            <td data-label="Delete" className="grabage">
                                <a href={"/#"} onClick={() => {
                                    onRemoveClick(item.projectNumber)
                                }}>
                                    {item.status === "NEW" ? <BsFillTrashFill color={"red"}/> : ""}
                                </a>
                            </td>
                        </tr>
                    ))}
                    {checkSelected()}
                    </tbody>
                </table>
            </form>
            <Pagination
                activePage={indexPage}
                itemsCountPerPage={itemPerPage}
                totalItemsCount={countItem}
                pageRangeDisplayed={3}
                onChange={handlePageChange}
            />
        </Container>
    );
};
export default ProjectList;