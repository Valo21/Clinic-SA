interface Appointment {
    date: Date
    description: string
    patientId: string,
    professionalId: string
}
enum AuthStateType {
    PENDING= 'PENDING',
    AUTHORIZED = 'AUTHORIZED',
    NOT_AUTHORIZED = 'NOT_AUTHORIZED'
}
enum PersonalID {
    DNI = 'DNI'
}
interface Patient {
    id: string
    personalIdType: PersonalID
    personalId: string
    firstName: string
    birth_date: Date
    lastName: string
    appointments: Appointment[]
    created_at: Date
    updated_at: Date
}

interface PatientHistory {
    id: string
    patient_id: string
    name: string
    age: number
    sex: string
    nationality: string
    occupation: string
    motivation: string
    actual_disease: string
    actual_disease_antecedent: string
    physical_exam: string
    created_at: Date
    updated_at: Date
}

interface Institution {
    name: string
    owner_id: string
    created_at: string
    updated_at: string
}

enum Specialty {
    Clinical = 'Clinical doctor',
    Dentist = 'Dentist'
}
interface Professional {
    id: string
    first_name: string
    last_name: string
    emai: string
    specialty: Specialty
    image_URL: string
    appointments: Appointment[]
    institution_id: string | null
    created_at: string
    updated_at: string
}

interface ApiResponse<T> {
    message: string,
    data: T
}

interface Tooth {
    number: number
    diagnostic: string | null
    treatment: string | null
}
interface Quadrant {
    number: number
    toothList: Tooth[]
}
interface DentalChart {
    quadrants: Quadrant[]
}
