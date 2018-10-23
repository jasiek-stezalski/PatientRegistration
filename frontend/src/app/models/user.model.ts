import {DoctorClinic} from './doctorClinic.model';

export class User {

  id?: string | number;
  username?: string;
  password?: string;
  confirmPassword?: string;
  firstName?: string;
  lastName?: string;
  role?: string;
  pesel?: string;
  email?: string;
  phoneNumber?: string;
  specialization?:string;
  doctorClinic?: DoctorClinic[];

}
