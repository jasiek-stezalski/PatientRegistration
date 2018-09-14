import {User} from './user.model';
import {VisitModel} from './visitModel.model';

export class Visit {

  id?: string | number;
  term?: string;
  status?: string;
  visitModel?: VisitModel;
  user?: User;

}

