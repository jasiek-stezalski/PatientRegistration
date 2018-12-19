import {User} from './user.model';
import {VisitModel} from './visitModel.model';

export class Visit {

  id?: string | number;
  start?: string;
  end?: string;
  text?: string;
  description?: string;
  rate?: number;
  visitModel?: VisitModel;
  user?: User;

  barColor?: string;
}

